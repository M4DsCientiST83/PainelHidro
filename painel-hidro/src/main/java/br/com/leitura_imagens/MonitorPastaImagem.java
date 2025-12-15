package br.com.leitura_imagens;

import java.io.File;
import java.util.*;

public class MonitorPastaImagem implements Sujeito 
{

    private final List<Observador> observadores = new ArrayList<>();
    private final Set<String> arquivosConhecidos = new HashSet<>();
    private final File pasta;

    private double volume;
    private String nome;

    public MonitorPastaImagem(String caminhoPasta) 
    {
        this.pasta = new File(caminhoPasta);
    }

    @Override
    public void cadastrar(Observador o) 
    {
        observadores.add(o);
    }

    @Override
    public void remover(Observador o) 
    {
        observadores.remove(o);
    }

    @Override
    public void notificar() 
    {
        for (Observador o : observadores) 
        {
           o.atualizar();
        }
    }

    public void setVolume(double v) 
    {
        volume = v;
    }

    public String getNome()
    {
        return nome;
    }

    public double getVolume()
    {
        return volume;
    }

    public void verificarNovosArquivos() 
    {
        File[] arquivos = pasta.listFiles((dir, name) ->
                name.endsWith(".png") || name.endsWith(".jpg"));

        if (arquivos == null) return;

        for (File f : arquivos) 
        {
            if (!arquivosConhecidos.contains(f.getName())) 
            {
                arquivosConhecidos.add(f.getName());
                nome = f.getName();
                notificar();
            }
        }
    }
}
