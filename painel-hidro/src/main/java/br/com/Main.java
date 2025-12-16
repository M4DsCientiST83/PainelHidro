package br.com;

import java.util.Scanner;
import br.com.utilitarios.Role;
import br.com.utilitarios.Usuario;

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
                    if (f.obterDadosUsuario("Cargo", usuario) == Role.ADMIN.name()) 
                    {
                        System.out.print("Nome do novo usuário: ");
                        String username = scanner.nextLine();

                        System.out.print("Senha do novo usuário: ");
                        String senha = scanner.nextLine();

                        System.out.print("ID do novo usuário: ");
                        int id = Integer.parseInt(scanner.nextLine());

                        Usuario u = new Usuario(id, username, senha); 
                        f.atualizarDadosUsuario("Cargo", u, "CLIENTE");

                        f.cadastrarUsuario(u);

                        break;
                    } 
                    else 
                    {
                        //f.verConsumo();
                    }
                    break;

                case 2:
                    if (Role.valueOf(f.obterDadosUsuario("Cargo", usuario)) == Role.ADMIN) 
                    {
                        f.exibirUsuarios();
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