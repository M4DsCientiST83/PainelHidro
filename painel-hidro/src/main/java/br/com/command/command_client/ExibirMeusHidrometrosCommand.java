package br.com.command.command_client;

import br.com.Fachada;

public class ExibirMeusHidrometrosCommand implements ComandoCliente 
{
    private Fachada fachada;
    private int idUsuario;

    public ExibirMeusHidrometrosCommand(Fachada fachada, int idUsuario) 
    {
        this.fachada = fachada;
        this.idUsuario = idUsuario;
    }

    @Override
    public void executar() 
    {
        fachada.exibirHidrometrosDoUsuario(idUsuario);
    }
}
