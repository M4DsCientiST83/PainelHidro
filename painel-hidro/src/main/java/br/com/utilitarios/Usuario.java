package br.com.utilitarios;

public class Usuario 
{
    private int id; 
    private String username;
    private String passwordHash;
    private Role role;

    public Usuario(int id, String u, String ph) 
    {
        this.id = id;
        this.username = u;
        this.passwordHash = ph;
    }

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

    public int getId()
    {
        return id;
    }

    public void setRole(Role role) 
    {
        this.role = role;
    }

    public void setUsername(String username) 
    {
        this.username = username;
    }

    public void setSenha(String senha) 
    {
        this.passwordHash = senha;
    }

    public void setId(int id) 
    {
        this.id = id;
    }
}
