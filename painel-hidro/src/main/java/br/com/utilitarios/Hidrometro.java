package br.com.utilitarios;

public class Hidrometro 
{
    private int id;
    private int usuario_id;
    private double volume_acumulado;

    public Hidrometro(int id)
    {
        this.id = id;
        this.usuario_id = 0;
        this.volume_acumulado = 0.0;
    }

    public void setId(int i)
    {
        id = i;
    }

    public void setUd(int ud)
    {
        usuario_id = ud;
    }

    public int getId()
    {
        return id;
    }

    public int getUd()
    {
        return usuario_id;
    }

    public double getVolumeAcumulado() {
        return volume_acumulado;
    }

    public void setVolumeAcumulado(double v) {
        this.volume_acumulado = v;
    }
}
