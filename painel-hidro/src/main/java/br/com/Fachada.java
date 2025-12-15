package br.com;

import java.util.Scanner;

import br.com.servicos_fachada.Autenticacao;

public class Fachada 
{
    private final Autenticacao auth;

    public Fachada(Autenticacao a)
    {
        auth = a;
    }

    public Usuario login(String u, String s)
    {
        return auth.autenticar(u, s);
    }

    public void menuAdmin() 
    {
        System.out.println("--- MENU ADMIN ---");
        System.out.println("1) Cadastrar novo usuário");
        System.out.println("2) Ativar monitoramento do hidrômetro");
        System.out.println("0) Sair");
    }

    public void menuCliente() 
    {
        System.out.println("--- MENU CLIENTE ---");
        System.out.println("1) Ver meus dados");
        System.out.println("2) Consultar leitura");
        System.out.println("0) Sair");    
    }
}