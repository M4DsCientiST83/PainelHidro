package br.com.factory;

public class UsuarioAdminCreator extends UsuarioCreator 
{
    @Override
    protected Usuario criarUsuario(int id, String nome, String senha) 
    {
        return new UsuarioAdmin(id, nome, senha);
    }
}
