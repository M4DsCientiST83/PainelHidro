package br.com.factory;

public class UsuarioClienteCreator extends UsuarioCreator 
{
    @Override
    protected Usuario criarUsuario(int id, String nome, String senha) 
    {
        return new UsuarioCliente(id, nome, senha);
    }
}
