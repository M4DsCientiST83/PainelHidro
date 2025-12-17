package br.com.command.command_admin;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import br.com.Fachada;
import br.com.servicos_fachada.MonitoramentoAssync;

public class ExibirMonitoramentoCommand implements ComandoAdmin {
    private Fachada fachada;

    public ExibirMonitoramentoCommand(Fachada fachada) {
        this.fachada = fachada;
    }

    @Override
    public void executar() {
        MonitoramentoAssync monitor = fachada.getMonitor();

        if (monitor == null || !monitor.isAtivo()) {
            System.out.println("Monitoramento não está ativo.");
            return;
        }

        System.out.println("Exibindo monitoramento. Pressione ENTER para sair...");

        final boolean[] sair = {false};

        Thread inputThread = new Thread(() -> {
            try {
                System.out.println("[Aguardando ENTER]");
                System.out.flush();
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                br.readLine();
                sair[0] = true;
            } catch (Exception e) {
                // ignore input errors
            }
        });

        inputThread.setDaemon(true);
        inputThread.start();

        while (monitor.isAtivo() && !sair[0]) {
            System.out.printf("Hidrômetro ID: %d | Volume acumulado: %.2f\n", monitor.getIdHidrometro(), monitor.getVolumeAcumulado());
            try {
                // exibir a cada 5s para acompanhar a frequência de persistência
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        System.out.println("Saindo da exibição de monitoramento.");
    }
}
