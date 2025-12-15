package br.com;

import java.util.Scanner;
import br.com.servicos_fachada.Autenticacao;
import br.com.utilitarios.Role;

public class Main 
{
    public static void main(String[] args)
    {
        Autenticacao auth = new Autenticacao();
        Fachada f = new Fachada(auth);
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

        if (usuario.getRole() == Role.ADMIN) 
        {
            f.menuAdmin();
        } 
        else 
        {
            f.menuCliente();
        }

        System.out.print("Escolha uma opção: ");
        int opcao = Integer.parseInt(scanner.nextLine());

        while (ok) 
        {
            switch (opcao) 
            {
                case 1:
                    if (usuario.getRole() == Role.ADMIN) 
                    {
                        //f.cadastrarUsuario();
                    } 
                    else 
                    {
                        //f.verConsumo();
                    }
                    break;

                case 2:
                    if (usuario.getRole() == Role.ADMIN) 
                    {
                        //f.listarUsuarios();
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