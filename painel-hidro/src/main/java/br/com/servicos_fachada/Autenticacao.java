package br.com.servicos_fachada;

import br.com.Usuario;
import br.com.dao.UsuarioDAO;

public class Autenticacao
{
    public Usuario autenticar(String u, String s)
    {
        UsuarioDAO ud = new UsuarioDAO();
        return ud.autenticar(u, s);
    }
}