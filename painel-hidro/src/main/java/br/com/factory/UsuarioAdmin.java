package br.com.factory;

import br.com.utilitarios.Role;

public class UsuarioAdmin extends Usuario 
{
    public UsuarioAdmin(int id, String nome, String senha) 
    {
        super(id, nome, senha);
        this.role = Role.ADMIN;
    }
}
