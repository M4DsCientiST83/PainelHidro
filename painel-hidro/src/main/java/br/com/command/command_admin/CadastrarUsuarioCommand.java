package br.com.command.command_admin;

import br.com.Fachada;
import br.com.factory.Usuario;
import br.com.utilitarios.Role;

public class CadastrarUsuarioCommand implements ComandoAdmin 
{
    private Fachada fachada;
    private int id;
    private String username;
    private String passwordhash;

    public CadastrarUsuarioCommand(Fachada fachada, int id, String un, String ps) 
    {
        this.fachada = fachada;
        this.id = id;
        this.username = un;
        this.passwordhash = ps;
    }

    @Override
    public void executar() 
    {
        Usuario usuario = fachada.criarUsuario(id, username, passwordhash, Role.CLIENTE);
        fachada.cadastrarUsuario(usuario);
    }
}