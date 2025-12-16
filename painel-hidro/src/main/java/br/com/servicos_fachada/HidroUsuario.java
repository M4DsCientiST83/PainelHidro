package br.com.servicos_fachada;

import br.com.utilitarios.Hidrometro;

import java.util.ArrayList;
import java.util.List;

public class HidroUsuario 
{
    List<Hidrometro> hidro;
    int[] id_usuario;

    public HidroUsuario()
    {
        hidro = new ArrayList<>();
    }
}
