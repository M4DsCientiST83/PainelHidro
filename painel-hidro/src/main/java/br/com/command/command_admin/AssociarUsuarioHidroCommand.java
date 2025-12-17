package br.com.command.command_admin;

import br.com.Fachada;

public class AssociarUsuarioHidroCommand implements ComandoAdmin 
{
    private Fachada fachada;
    private int idHidro;
    private int idUsuario;

    public AssociarUsuarioHidroCommand(Fachada fachada, int idHidro, int idUsuario) 
    {
        this.fachada = fachada;
        this.idHidro = idHidro;
        this.idUsuario = idUsuario;
    }

    @Override
    public void executar() 
    {
        fachada.associarUsuarioHidro(idHidro, idUsuario);
    }
}

