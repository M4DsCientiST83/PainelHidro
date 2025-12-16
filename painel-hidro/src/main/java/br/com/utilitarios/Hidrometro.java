package br.com.utilitarios;

public class Hidrometro 
{
    private int id;
    private int usuario_id;

    public Hidrometro(int id)
    {
        this.id = id;
        this.usuario_id = 0;
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
}
