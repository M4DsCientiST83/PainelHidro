package br.com.command.command_client;

import br.com.Fachada;

public class AtivarMeuHidroCommand implements ComandoCliente 
{
    private Fachada fachada;
    private String caminhoPasta;
    private int idHidrometro;

    public AtivarMeuHidroCommand(Fachada fachada, String caminhoPasta, int idHidrometro) 
    {
        this.fachada = fachada;
        this.caminhoPasta = caminhoPasta;
        this.idHidrometro = idHidrometro;
    }

    @Override
    public void executar() 
    {
        fachada.ativarMonitoramentoAssincrono(caminhoPasta, idHidrometro);
    }
}
