package br.com.servicos_fachada;

import br.com.factory.Usuario;

public abstract class AutenticacaoTemplate 
{
    public final Usuario autenticar(String username, String senha) 
    {
        if (!validarCredenciais(username, senha)) 
        {
            return null;
        }

        Usuario usuario = buscarUsuario(username, senha);

        if (usuario == null) 
        {
            return null;
        }

        if (!verificarAutorizacao(usuario)) 
        {
            return null;
        }

        return usuario;
    }

    protected abstract boolean validarCredenciais(String username, String senha);

    protected abstract Usuario buscarUsuario(String username, String senha);

    protected abstract boolean verificarAutorizacao(Usuario usuario);
}
