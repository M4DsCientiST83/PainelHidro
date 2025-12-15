package br.com;

import br.com.servicos_fachada.Autenticacao;

public class Fachada 
{
    private final Autenticacao auth;

    public Fachada(Autenticacao a)
    {
        auth = a;
    }

    public boolean login(String u, String s)
    {
        return auth.autenticar(u, s);
    }

    public void menu()
    {
        System.out.print("Qual operação deseja realizar?\n1 - Registrar ");
    }
}