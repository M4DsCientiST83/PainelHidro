package br.com.command.command_admin;

import br.com.Fachada;

public class AtualizarUsuarioCommand implements ComandoAdmin 
{
    private Fachada fachada;
    private String tipo;
    private int id;
    private String dado;

    public AtualizarUsuarioCommand(Fachada fachada, String tipo, int id, String dado) 
    {
        this.fachada = fachada;
        this.tipo = tipo;
        this.id = id;
        this.dado = dado;
    }

    @Override
    public void executar() 
    {
        fachada.atualizarDadosUsuario(tipo, id, dado);
    }
}

