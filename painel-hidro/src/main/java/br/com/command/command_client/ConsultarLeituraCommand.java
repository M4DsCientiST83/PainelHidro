package br.com.command.command_client;

import br.com.Fachada;
import java.util.Scanner;

public class ConsultarLeituraCommand implements ComandoCliente {
    private Fachada fachada;
    private int idUsuario;

    public ConsultarLeituraCommand(Fachada fachada, int idUsuario) {
        this.fachada = fachada;
        this.idUsuario = idUsuario;
    }

    @Override
    public void executar() {
        // Primeiro exibe os hidrômetros do usuário para ele escolher
        fachada.exibirHidrometrosDoUsuario(idUsuario);

        System.out.println("Digite o ID do hidrômetro para consultar a leitura da vazão/volume:");
        Scanner scanner = new Scanner(System.in);
        // Não fechar o scanner pois System.in é global

        try {
            int idHidrometro = Integer.parseInt(scanner.nextLine());

            // Aqui seria ideal verificar se o hidrômetro pertence ao usuário antes de
            // consultar
            // Mas vamos confiar na escolha válida por enquanto ou deixar o DAO lançar
            // exceção senão encontrar

            double volume = fachada.consultarLeitura(idHidrometro);

            System.out.println("-----------------------------------------------------");
            System.out.printf("Última leitura do Hidrômetro %d: %.5f m³\n", idHidrometro, volume);
            System.out.println("-----------------------------------------------------");

        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
