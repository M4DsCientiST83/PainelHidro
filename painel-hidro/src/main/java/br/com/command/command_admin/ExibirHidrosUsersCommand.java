package br.com.command.command_admin;

import br.com.Fachada;

public class ExibirHidrosUsersCommand implements ComandoAdmin 
{
    private Fachada fachada;

    public ExibirHidrosUsersCommand(Fachada fachada) 
    {
        this.fachada = fachada;
    }

    @Override
    public void executar() 
    {
        fachada.exibirHidrosUsers();
    }
}

