package br.com.servicos_fachada;

import br.com.leitura_imagens.MonitorPastaImagem;
import br.com.leitura_imagens.ProcessadorImagem;
import br.com.dao.HidrometroDAO;
import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;
import java.io.IOException;

public class MonitoramentoAssync {
    private String caminhoPasta;
    private MonitorPastaImagem monitor;
    private ProcessadorImagem proc;
    private double volume_acumulado;
    private Thread monitorThread;
    private volatile boolean ativo = false;
    private int idHidrometro;

    public MonitoramentoAssync(String c, int idHidrometro) {
        this.caminhoPasta = c;
        this.idHidrometro = idHidrometro;
        monitor = new MonitorPastaImagem(caminhoPasta);
        proc = new ProcessadorImagem(monitor);

        monitor.cadastrar(proc);
        monitor.inicializarArquivosConhecidos();
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
                            System.out.println("Novo arquivo detectado: " + filename.toString());

                            // executar verificação/notify (síncrono)
                            monitor.verificarNovosArquivos();
                            volume_acumulado = monitor.getVolume();

                            try {
                                dao.atualizarVolumeAcumulado(idHidrometro, volume_acumulado);
                            } catch (Exception e) {
                                System.out.println("Erro ao persistir volume: " + e.getMessage());
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
}
