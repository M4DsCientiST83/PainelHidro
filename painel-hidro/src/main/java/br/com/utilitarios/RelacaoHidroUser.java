package br.com.utilitarios;

public class RelacaoHidroUser 
{
    private int codigoHidrometro;
    private String nomeUsuario; 

    public RelacaoHidroUser(int codigoHidrometro, String nomeUsuario) 
    {
        this.codigoHidrometro = codigoHidrometro;
        this.nomeUsuario = nomeUsuario;
    }

    public int getCodigoHidrometro() 
    {
        return codigoHidrometro;
    }

    public String getNomeUsuario() 
    {
        return nomeUsuario;
    }
}
