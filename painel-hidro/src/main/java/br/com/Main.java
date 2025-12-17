package br.com;

import java.util.Scanner;

import br.com.command.command_admin.*;
import br.com.command.command_client.*;
import br.com.factory.Usuario;
import br.com.utilitarios.*;

public class Main {
    public static void exibir_menu(Fachada f, Usuario u) {
        if (u.getRole() == Role.ADMIN) {
            f.menuAdmin();
        } else {
            f.menuCliente();
        }
    }

    public static void main(String[] args) {
        Fachada f = new Fachada();
        MenuAdminInvoker adminInvoker = new MenuAdminInvoker();
        MenuClienteInvoker clienteInvoker = new MenuClienteInvoker();
        Scanner scanner = new Scanner(System.in);

        Usuario usuario = null;
        boolean ok = false;

        while (!ok) {
            System.out.print("Usuário: ");
            String username = scanner.nextLine();

            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            usuario = f.login(username, senha);

            if (usuario != null) {
                ok = true;
            }
        }

        while (ok) {
            exibir_menu(f, usuario);

            System.out.print("Escolha uma opção: \n");
            int opcao;

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida");
                continue;
            }

            ComandoAdmin adminCmd = null;
            ComandoCliente clienteCmd = null;

            switch (opcao) {
                case 1:
                    if (usuario.getRole() == Role.ADMIN) {
                        System.out.print("Nome do novo usuário: ");
                        String username = scanner.nextLine();

                        System.out.print("Senha do novo usuário: ");
                        String senha = scanner.nextLine();

                        System.out.print("ID do novo usuário: ");
                        int id = Integer.parseInt(scanner.nextLine());

                        adminCmd = new CadastrarUsuarioCommand(f, id, username, senha);
                    } else {
                        clienteCmd = new ExibirMeusHidrometrosCommand(f, usuario.getId());
                    }
                    break;

                case 2:
                    if (usuario.getRole() == Role.ADMIN) {
                        adminCmd = new ExibirUsuariosCommand(f);
                    } else {
                        // TODO: Ver meus dados (future implementation)
                        System.out.println("Funcionalidade em desenvolvimento");
                    }
                    break;

                case 3:
                    if (usuario.getRole() == Role.ADMIN) {
                        System.out.print("Digite o id do usuário:\n");
                        int id = Integer.parseInt(scanner.nextLine());

                        System.out.print("Digite o dado que quer alterar:\n");
                        String tipo = scanner.nextLine();

                        System.out.print("Digite a alteração:\n");
                        String dado = scanner.nextLine();

                        adminCmd = new AtualizarUsuarioCommand(f, tipo, id, dado);
                    } else {
                        // TODO: Consultar leitura (future implementation)
                        System.out.println("Funcionalidade em desenvolvimento");
                    }
                    break;

                case 4:
                    if (usuario.getRole() == Role.ADMIN) {
                        System.out.print("Digite o id do usuário a ser removido:\n");
                        int id = Integer.parseInt(scanner.nextLine());

                        adminCmd = new RemoverUsuarioCommand(f, id);
                    }
                    break;

                case 5:
                    if (usuario.getRole() == Role.ADMIN) {
                        System.out.print("Digite o id do hidrômetro:\n");
                        int id = Integer.parseInt(scanner.nextLine());

                        adminCmd = new CadastrarHidrometroCommand(f, id);
                    }
                    break;

                case 6:
                    if (usuario.getRole() == Role.ADMIN) {
                        System.out.print("Digite o id do hidrômetro que quer associar:\n");
                        int idH = Integer.parseInt(scanner.nextLine());

                        System.out.print("Digite o id do usuário proprietário:\n");
                        int idU = Integer.parseInt(scanner.nextLine());

                        adminCmd = new AssociarUsuarioHidroCommand(f, idH, idU);
                    }
                    break;

                case 7:
                    if (usuario.getRole() == Role.ADMIN) {
                        adminCmd = new ExibirHidrosUsersCommand(f);
                    }
                    break;

                case 8:
                    if (usuario.getRole() == Role.ADMIN) {
                        System.out.print("Digite o id do hidrômetro a ser removido:\n");
                        int id = Integer.parseInt(scanner.nextLine());

                        adminCmd = new RemoverHidrometroCommand(f, id);
                    }
                    break;

                case 9:
                    if (usuario.getRole() == Role.ADMIN) {
                        System.out.print("Digite o id do hidrômetro a ser monitorado:\n");
                        int idH = Integer.parseInt(scanner.nextLine());

                        System.out.print("Digite o caminho da pasta para monitoramento:\n");
                        String caminho = scanner.nextLine();

                        adminCmd = new AtivarMonitoramentoCommand(f, caminho, idH);
                    }
                    break;

                case 10:
                    if (usuario.getRole() == Role.ADMIN) {
                        adminCmd = new ExibirMonitoramentoCommand(f);
                    }
                    break;

                case 11:
                    if (usuario.getRole() == Role.ADMIN) {
                        adminCmd = new DesativarMonitoramentoCommand(f);
                    }
                    break;

                case 0:
                    System.out.println("Saindo...");
                    ok = false;
                    break;

                default:
                    System.out.println("Opção inválida");
            }

            if (adminCmd != null) {
                adminInvoker.setComando(adminCmd);
                adminInvoker.executar();
            } else if (clienteCmd != null) {
                clienteInvoker.setComando(clienteCmd);
                clienteInvoker.executar();
            }
        }
        scanner.close();
    }
}