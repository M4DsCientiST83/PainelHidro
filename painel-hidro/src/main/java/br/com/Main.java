package br.com;

import java.util.Scanner;
import br.com.utilitarios.*;

public class Main 
{
    public static void exibir_menu(Fachada f, Usuario u)
    {
        if (u.getRole() == Role.ADMIN) 
            {
                f.menuAdmin();
            } 
            else 
            {
                f.menuCliente();
            }
    }
    public static void main(String[] args)
    {
        Fachada f = new Fachada();
        Scanner scanner = new Scanner(System.in);
        
        Usuario usuario = null;
        boolean ok = false;

        while(!ok)
        {
            System.out.print("Usuário: ");
            String username = scanner.nextLine();

            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            usuario = f.login(username, senha);
            
            if (usuario != null)
            {
                ok = true;
            } 
        }

        while (ok) 
        {
            exibir_menu(f, usuario);
        
            System.out.print("Escolha uma opção: \n");
            int opcao;

            try 
            {
                opcao = Integer.parseInt(scanner.nextLine());
            } 
            catch (NumberFormatException e) 
            {
                System.out.println("Opção inválida");
                continue;
            }

            switch (opcao) 
            {
                case 1:
                    if (f.obterDadosUsuario("Cargo", f.obterId(usuario)) == Role.ADMIN.name()) 
                    {
                        System.out.print("Nome do novo usuário: ");
                        String username = scanner.nextLine();

                        System.out.print("Senha do novo usuário: ");
                        String senha = scanner.nextLine();

                        System.out.print("ID do novo usuário: ");
                        int id = Integer.parseInt(scanner.nextLine());

                        Usuario u = f.criarUsuario(id, username, senha);
                        f.atualizarDadosUsuario("Cargo", f.obterId(u), "CLIENTE");

                        f.cadastrarUsuario(u);

                        break;
                    } 
                    else 
                    {
                        //f.verConsumo();
                    }
                    break;

                case 2:
                    if (f.obterDadosUsuario("Cargo", f.obterId(usuario)) == Role.ADMIN.name()) 
                    {
                        f.exibirUsuarios();
                    } 
                    else 
                    {
                        //f.verHistorico();
                    }
                    break;
                
                case 3:
                    if (f.obterDadosUsuario("Cargo", f.obterId(usuario)) == Role.ADMIN.name()) 
                    {
                        System.out.print("Digite o id do usuário:\n");
                        int id = Integer.parseInt(scanner.nextLine());

                        System.out.print("Digite o dado que quer alterar:\n");
                        String tipo_dado = scanner.nextLine();

                        System.out.print("Digite a alteração:\n");
                        String dado = scanner.nextLine();

                        f.atualizarDadosUsuario(tipo_dado, id, dado);
                    } 
                    else 
                    {
                        //f.verHistorico();
                    }
                    break;

                case 4:
                    if (f.obterDadosUsuario("Cargo", f.obterId(usuario)) == Role.ADMIN.name()) 
                    {
                        System.out.print("Digite o id do usuário a ser removido:\n");
                        int id = Integer.parseInt(scanner.nextLine());

                        f.removerUsuario(id);
                    } 
                    else 
                    {
                        //f.verHistorico();
                    }
                    break;
                    
                case 5:
                    if (f.obterDadosUsuario("Cargo", f.obterId(usuario)) == Role.ADMIN.name()) 
                    {
                        System.out.print("Digite o id do hidrômetro:\n");
                        int id = Integer.parseInt(scanner.nextLine());

                        Hidrometro h = f.criarHidro(id);

                        f.cadastrarHidro(h);
                    } 
                    else 
                    {
                        //f.verHistorico();
                    }
                    break;

                case 6:
                    if (f.obterDadosUsuario("Cargo", f.obterId(usuario)) == Role.ADMIN.name()) 
                    {
                        System.out.print("Digite o id do hidrômetro que quer associar:\n");
                        int id_h = Integer.parseInt(scanner.nextLine());

                        System.out.print("Digite o id do usuário proprietário:\n");
                        int id_u = Integer.parseInt(scanner.nextLine());
                        
                        f.associarUsuarioHidro(id_h, id_u);
                    } 
                    else 
                    {
                        //f.verHistorico();
                    }
                    break;
                    
                case 7:
                    if (f.obterDadosUsuario("Cargo", f.obterId(usuario)) == Role.ADMIN.name()) 
                    {
                        f.exibirHidrosUsers();
                    } 
                    else 
                    {
                        //f.verHistorico();
                    }
                    break;
                
                case 8:
                    if (f.obterDadosUsuario("Cargo", f.obterId(usuario)) == Role.ADMIN.name()) 
                    {
                        System.out.print("Digite o id do hidrômetro a ser removido:\n");
                        int id = Integer.parseInt(scanner.nextLine());

                        f.removerHidrometro(id);
                    } 
                    else 
                    {
                        //f.verHistorico();
                    }
                    break;

                case 0:
                    System.out.println("Saindo...");
                    ok = false;
                    break;

                default:
                    System.out.println("Opção inválida");
            }
        }
        scanner.close();
    }
}