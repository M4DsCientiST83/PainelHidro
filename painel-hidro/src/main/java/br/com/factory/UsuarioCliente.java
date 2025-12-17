package br.com.factory;

import br.com.utilitarios.Role;

public class UsuarioCliente extends Usuario
{
    public UsuarioCliente(int id, String nome, String senha) 
    {
        super(id, nome, senha);
        this.role = Role.CLIENTE;
    }
}

