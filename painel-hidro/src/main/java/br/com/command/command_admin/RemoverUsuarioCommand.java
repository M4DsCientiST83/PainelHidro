package br.com.command.command_admin;

import br.com.Fachada;

public class RemoverUsuarioCommand implements ComandoAdmin 
{
    private Fachada fachada;
    private int idUsuario;

    public RemoverUsuarioCommand(Fachada fachada, int idUsuario) 
    {
        this.fachada = fachada;
        this.idUsuario = idUsuario;
    }

    @Override
    public void executar() 
    {
        fachada.removerUsuario(idUsuario);
    }
}
