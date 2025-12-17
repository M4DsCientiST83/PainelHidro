package br.com.command.command_client;

import br.com.Fachada;

public class DesativarMeuHidroCommand implements ComandoCliente {
    private Fachada fachada;

    public DesativarMeuHidroCommand(Fachada fachada) {
        this.fachada = fachada;
    }

    @Override
    public void executar() {
        java.util.Set<Integer> ativos = fachada.getMonitoresAtivos();

        if (ativos.isEmpty()) {
            System.out.println("Nenhum monitoramento ativo para desativar.");
            return;
        }

        int id = -1;

        if (ativos.size() == 1) {
            id = ativos.iterator().next();
        } else {
            System.out.println("Hidrômetros sendo monitorados: " + ativos);
            System.out.print("Digite o ID do hidrômetro para desativar: ");
            try {
                java.util.Scanner scanner = new java.util.Scanner(System.in);
                // Não fechar o scanner pois System.in é global
                id = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("ID inválido.");
                return;
            }
        }

        fachada.desativarMonitoramentoAssincrono(id);
    }
}
