package br.com.leitura_imagens;

import java.io.File;
import java.util.*;

public class MonitorPastaImagem implements Sujeito {
    private final List<Observador> observadores = new ArrayList<>();
    private final Set<String> arquivosConhecidos = new HashSet<>();
    private final File pasta;

    private double volume;
    private String nome;

    // Histórico de volumes para detecção de consumo elevado (12 iterações)
    private final LinkedList<Double> historicoVolumes = new LinkedList<>();
    private boolean alertaConsumoElevado = false;

    public MonitorPastaImagem(String caminhoPasta) {
        this.pasta = new File(caminhoPasta);
    }

    @Override
    public void cadastrar(Observador o) {
        observadores.add(o);
    }

    @Override
    public void remover(Observador o) {
        observadores.remove(o);
    }

    @Override
    public void notificar() {
        for (Observador o : observadores) {
            o.atualizar();
        }
    }

    public void setVolume(double v) {
        volume = v;

        // Lógica de alerta: > 0.8m³ em 12 iterações
        historicoVolumes.add(v);

        // Manter tamanho máximo de 13 (atual + 12 anteriores)
        if (historicoVolumes.size() > 13) {
            historicoVolumes.removeFirst();
        }

        // Se temos histórico suficiente (ou pelo menos 2 para comparar)
        if (historicoVolumes.size() > 1) {
            double volumeInicial = historicoVolumes.getFirst();
            double diff = v - volumeInicial;

            if (diff > 0.8) {
                alertaConsumoElevado = true;
                // System.out.println("ALERTA: Consumo elevado detectado! " +
                // String.format("%.5f", diff) + "m³ nas últimas iterações.");
            } else {
                alertaConsumoElevado = false;
            }
        }
    }

    public boolean isAlertaConsumoElevado() {
        return alertaConsumoElevado;
    }

    public String getNome() {
        return nome;
    }

    public double getVolume() {
        return volume;
    }

    public Set<String> getArquivosConhecidos() {
        return arquivosConhecidos;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void verificarNovosArquivos() {
        File[] arquivos = pasta
                .listFiles((dir, name) -> name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg"));

        if (arquivos == null)
            return;

        for (File f : arquivos) {
            if (!arquivosConhecidos.contains(f.getName())) {
                arquivosConhecidos.add(f.getName());
                nome = f.getName();
                notificar();
            }
        }
    }

    public void inicializarArquivosConhecidos() {
        File[] arquivos = pasta
                .listFiles((dir, name) -> name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg"));

        if (arquivos == null)
            return;

        for (File f : arquivos) {
            arquivosConhecidos.add(f.getName());
        }
    }

    public String getPastaPath() {
        return pasta.getAbsolutePath();
    }
}
