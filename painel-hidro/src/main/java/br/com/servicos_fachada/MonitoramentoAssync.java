package br.com.servicos_fachada;

import br.com.leitura_imagens.MonitorPastaImagem;
import br.com.leitura_imagens.ProcessadorImagem;

public class MonitoramentoAssync 
{
    private String caminhoPasta;
    private MonitorPastaImagem monitor;
    private ProcessadorImagem proc;
    private double volume_acumulado;

    public MonitoramentoAssync(String c)
    {
        caminhoPasta = c;
        monitor = new MonitorPastaImagem(caminhoPasta);
        proc = new ProcessadorImagem(monitor);

        monitor.cadastrar(proc);
    }

    public void ativarMonitoramento()
    {
        while (true) 
        {
            monitor.verificarNovosArquivos();   
            volume_acumulado = monitor.getVolume();
        }
    }

}
