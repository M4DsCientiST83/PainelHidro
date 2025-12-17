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
        java.util.Set<Integer> ativos = fachada.getMonitoresAtivos();

        if (ativos.isEmpty()) {
            System.out.println("Nenhum monitoramento ativo.");
            return;
        }

        MonitoramentoAssync monitor = null;

        if (ativos.size() == 1) {
            monitor = fachada.getMonitorUnico();
        } else {
            System.out.println("Hidrômetros sendo monitorados: " + ativos);
            System.out.print("Digite o ID do hidrômetro para exibir: ");
            try {
                java.util.Scanner scanner = new java.util.Scanner(System.in);
                // Não fechar o scanner poisSystem.in é global
                int id = Integer.parseInt(scanner.nextLine());
                monitor = fachada.getMonitor(id);
            } catch (Exception e) {
                System.out.println("ID inválido.");
                return;
            }
        }

        if (monitor == null || !monitor.isAtivo()) {
            System.out.println("Monitoramento não encontrado ou inativo.");
            return;
        }

        System.out.println("Exibindo monitoramento. Pressione ENTER para sair...");

        // Ativar exibição de mensagens de detecção
        monitor.setExibindoMonitoramento(true);

        final boolean[] sair = { false };

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
            System.out.printf("Hidrômetro ID: %d | Volume acumulado: %.5f\n", monitor.getIdHidrometro(),
                    monitor.getVolumeAcumulado());

            if (monitor.isAlertaConsumoElevado()) {
                System.out.println("⚠️ ALERTA: Consumo elevado detectado! (> 0.8m³ em 12 iterações)");
            }

            try {
                // exibir a cada 5s para acompanhar a frequência de persistência
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        // Desativar exibição de mensagens de detecção
        monitor.setExibindoMonitoramento(false);

        System.out.println("Saindo da exibição de monitoramento.");
    }
}