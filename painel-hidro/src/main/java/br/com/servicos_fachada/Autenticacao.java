package br.com.servicos_fachada;

import br.com.dao.UsuarioDAO;
import br.com.utilitarios.Usuario;

public class Autenticacao
{
    public Usuario autenticar(String u, String s)
    {
        UsuarioDAO ud = new UsuarioDAO();
        return ud.autenticar(u, s);
    }
}