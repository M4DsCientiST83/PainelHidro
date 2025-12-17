package br.com.factory;

public abstract class UsuarioCreator 
{
    public Usuario criar(int id, String nome, String senha) 
    {
        Usuario usuario = criarUsuario(id, nome, senha);
        return usuario;
    }

    protected abstract Usuario criarUsuario(int id, String nome, String senha);
}
