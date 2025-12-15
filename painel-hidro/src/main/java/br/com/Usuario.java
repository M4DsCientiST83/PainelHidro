package br.com;

import br.com.utilitarios.Role;

public class Usuario 
{
    public String username;
    public String passwordHash;
    public Role role;

    public Usuario() {}

    public Role getRole() 
    {
        return role;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPasswordHash()
    {
        return passwordHash;
    }

    public void setRole(Role role) 
    {
        this.role = role;
    }
}
