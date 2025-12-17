package br.com.servicos_fachada;

import br.com.leitura_imagens.MonitorPastaImagem;
import br.com.leitura_imagens.ProcessadorImagem;
import br.com.dao.HidrometroDAO;
import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;
import java.io.IOException;
import java.io.File;

public class MonitoramentoAssync {
    private String caminhoPasta;
    private MonitorPastaImagem monitor;
    private ProcessadorImagem proc;
    private double volume_acumulado;
    private Thread monitorThread;
    private volatile boolean ativo = false;
    private volatile boolean exibindoMonitoramento = false;
    private int idHidrometro;

    public MonitoramentoAssync(String c, int idHidrometro) {
        this.caminhoPasta = c;
        this.idHidrometro = idHidrometro;

        // Buscar tipo do hidrômetro no banco de dados
        HidrometroDAO dao = new HidrometroDAO();
        char tipoHidrometro = dao.buscarTipo(idHidrometro);

        monitor = new MonitorPastaImagem(caminhoPasta);
        proc = new ProcessadorImagem(monitor, tipoHidrometro);

        monitor.cadastrar(proc);
        // Processar arquivos existentes para obter volume inicial
        processarArquivosExistentes();
    }

    private void processarArquivosExistentes() {
        File pasta = new File(caminhoPasta);
        File[] arquivos = pasta
                .listFiles((dir, name) -> name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg"));

        if (arquivos == null || arquivos.length == 0) {
            return;
        }

        // Ordenar por nome para processar em ordem
        java.util.Arrays.sort(arquivos, (a, b) -> a.getName().compareTo(b.getName()));

        System.out.println("Encontrados " + arquivos.length + " arquivo(s) existente(s).");

        // Marcar todos como conhecidos (exceto o último)
        for (int i = 0; i < arquivos.length - 1; i++) {
            monitor.getArquivosConhecidos().add(arquivos[i].getName());
        }

        // Processar apenas o último arquivo para obter volume atual
        if (arquivos.length > 0) {
            File ultimoArquivo = arquivos[arquivos.length - 1];
            System.out.println("Processando último arquivo para obter volume atual: " + ultimoArquivo.getName());

            monitor.getArquivosConhecidos().add(ultimoArquivo.getName());
            monitor.setNome(ultimoArquivo.getName());
            monitor.notificar(); // Processa o arquivo via OCR

            // Aguardar processamento OCR completar
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            volume_acumulado = monitor.getVolume();
            System.out.println("Volume inicial: " + volume_acumulado);

            // Persistir volume inicial
            HidrometroDAO dao = new HidrometroDAO();
            try {
                dao.atualizarVolumeAcumulado(idHidrometro, volume_acumulado);
            } catch (Exception e) {
                System.out.println("Erro ao persistir volume inicial: " + e.getMessage());
            }
        }
    }

    public void ativarMonitoramento() {
        if (ativo) {
            System.out.println("Monitoramento já está ativo!");
            return;
        }
        // usar WatchService para reagir imediatamente à criação de novos arquivos
        ativo = true;
        monitorThread = new Thread(() -> {
            System.out.println("Monitoramento iniciado em segundo plano (watch service)...");
            HidrometroDAO dao = new HidrometroDAO();

            Path dir = Paths.get(caminhoPasta);
            try (WatchService watcher = FileSystems.getDefault().newWatchService()) {
                dir.register(watcher, ENTRY_CREATE);

                while (ativo) {
                    WatchKey key;
                    try {
                        key = watcher.take();
                    } catch (InterruptedException x) {
                        Thread.currentThread().interrupt();
                        break;
                    }

                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();

                        if (kind == OVERFLOW) {
                            continue;
                        }

                        if (kind == ENTRY_CREATE) {
                            // quando um novo arquivo é criado, verificar e processar
                            WatchEvent<Path> ev = (WatchEvent<Path>) event;
                            Path filename = ev.context();

                            // Só exibir mensagem se o monitoramento estiver sendo exibido
                            if (exibindoMonitoramento) {
                                System.out.println("Novo arquivo detectado: " + filename.toString());
                            }

                            // Processar o arquivo manualmente para garantir que o observer seja notificado
                            String nomeArquivo = filename.toString();
                            if ((nomeArquivo.endsWith(".png") || nomeArquivo.endsWith(".jpg")
                                    || nomeArquivo.endsWith(".jpeg"))
                                    && !monitor.getArquivosConhecidos().contains(nomeArquivo)) {

                                // Aguardar um pouco para garantir que o arquivo foi completamente escrito
                                try {
                                    Thread.sleep(200);
                                } catch (InterruptedException ie) {
                                    Thread.currentThread().interrupt();
                                }

                                monitor.getArquivosConhecidos().add(nomeArquivo);
                                monitor.setNome(nomeArquivo);
                                monitor.notificar(); // Isso vai chamar ProcessadorImagem.atualizar()

                                // Aguardar o processamento OCR completar (OCR tenta múltiplos recortes)
                                try {
                                    Thread.sleep(3000); // Aumentado para 3s para permitir tentativas múltiplas
                                } catch (InterruptedException ie) {
                                    Thread.currentThread().interrupt();
                                }

                                volume_acumulado = monitor.getVolume();

                                if (exibindoMonitoramento) {
                                    System.out.println("Volume atualizado para: " + volume_acumulado);
                                }

                                try {
                                    dao.atualizarVolumeAcumulado(idHidrometro, volume_acumulado);
                                } catch (Exception e) {
                                    System.out.println("Erro ao persistir volume: " + e.getMessage());
                                }
                            }
                        }
                    }

                    boolean valid = key.reset();
                    if (!valid) {
                        break;
                    }
                }
            } catch (IOException ioe) {
                System.out.println("Erro no WatchService: " + ioe.getMessage());
                ioe.printStackTrace();
            }
        });

        monitorThread.setDaemon(true);
        monitorThread.start();
    }

    public void desativarMonitoramento() {
        ativo = false;
        if (monitorThread != null) {
            monitorThread.interrupt();
        }
    }

    public double getVolumeAcumulado() {
        return volume_acumulado;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public int getIdHidrometro() {
        return idHidrometro;
    }

    public void setExibindoMonitoramento(boolean exibindo) {
        this.exibindoMonitoramento = exibindo;
    }
}
