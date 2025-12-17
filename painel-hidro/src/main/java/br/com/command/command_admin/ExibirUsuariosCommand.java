package br.com.command.command_admin;

import br.com.Fachada;

public class ExibirUsuariosCommand implements ComandoAdmin 
{
    private Fachada fachada;

    public ExibirUsuariosCommand(Fachada fachada) 
    {
        this.fachada = fachada;
    }

    @Override
    public void executar() 
    {
        fachada.exibirUsuarios();
    }
}
