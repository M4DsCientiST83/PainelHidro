package br.com.command.command_admin;

import br.com.Fachada;

public class RemoverHidrometroCommand implements ComandoAdmin 
{
    private Fachada fachada;
    private int idHidro;

    public RemoverHidrometroCommand(Fachada fachada, int idHidro) 
    {
        this.fachada = fachada;
        this.idHidro = idHidro;
    }

    @Override
    public void executar() 
    {
        fachada.removerHidrometro(idHidro);
    }
}

