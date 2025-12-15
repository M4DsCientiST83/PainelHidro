package br.com;

import java.util.Scanner;
import br.com.servicos_fachada.Autenticacao;

public class Main 
{
    public static void main(String[] args)
    {
        Autenticacao auth = new Autenticacao();
        Fachada f = new Fachada(auth);
        Scanner scanner = new Scanner(System.in);

        boolean ok = false;

        while(!ok)
        {
            System.out.print("Usuário: ");
            String username = scanner.nextLine();

            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            f.login(username, senha);
            ok = true;
        }

        

        
        scanner.close();
    }
}