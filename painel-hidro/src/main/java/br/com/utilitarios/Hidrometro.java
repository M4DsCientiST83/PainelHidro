package br.com.utilitarios;

public class Hidrometro 
{
    private double diametroEntrada;
    private double velMediaFluxoAgua;
    private int tempoSimulacao;

    public Hidrometro(double de, double vmfa, int ts)
    {
        diametroEntrada = de;
        velMediaFluxoAgua = vmfa;
        tempoSimulacao = ts;
    }
}
