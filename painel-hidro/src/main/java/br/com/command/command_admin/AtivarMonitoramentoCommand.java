package br.com.command.command_admin;

import br.com.Fachada;

public class AtivarMonitoramentoCommand implements ComandoAdmin 
{
    private Fachada fachada;
    private String caminhoPasta;
    private int idHidrometro;

    public AtivarMonitoramentoCommand(Fachada fachada, String caminhoPasta, int idHidrometro) 
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
