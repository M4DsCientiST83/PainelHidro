package br.com.servicos_fachada;

import br.com.factory.Usuario;

public class Autenticacao 
{
    private AutenticacaoTemplate autenticacaoTemplate;

    public Autenticacao() 
    {
        this.autenticacaoTemplate = new AutenticacaoPadrao();
    }

    public Usuario autenticar(String u, String s) 
    {
        return autenticacaoTemplate.autenticar(u, s);
    }
}