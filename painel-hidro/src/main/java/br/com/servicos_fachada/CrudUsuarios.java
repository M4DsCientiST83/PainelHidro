package br.com.servicos_fachada;

import br.com.dao.UsuarioDAO;
import br.com.utilitarios.Role;
import br.com.utilitarios.Usuario;

import java.util.List;

public class CrudUsuarios 
{
    private UsuarioDAO usuariodao;

    public CrudUsuarios()
    {
        usuariodao = new UsuarioDAO();
    }

    public boolean criarUsuario(Usuario u)
    {
        if (u.getUsername() == null || u.getUsername().isBlank())
        {
            throw new IllegalArgumentException("Nome de usuário inválido");
        }

        usuariodao.salvar(u);
        return true;
    }

    public void exibirUsuarios()
    {
        List<Usuario> lista = usuariodao.retornarTodos();

        System.out.println("Usuários do sistema:\n");

        for (Usuario u : lista) 
        {
            System.out.println(
                "ID: " + u.getId() +
                " | Nome: " + u.getUsername() +
                " | Cargo: " + u.getRole()
            );
        }

        System.out.println("\n");
    }

    public void atualizarUsuario(String tipo, Usuario u, String dado)
    {
        if (tipo.equals("Nome"))
        {
            u.setUsername(dado);
        }
        else if (tipo.equals("ID"))
        {
            u.setId(Integer.parseInt(dado));
        }
        else if (tipo.equals("Cargo"))
        {
            u.setRole(Role.valueOf(dado));
        }
        System.err.println("Usuário ou tipo de dado inválido!\n"); 
    }

    public String obterDado(String tipo, Usuario u)
    {
        if (tipo.equals("Nome"))
        {
            return u.getUsername();
        }
        else if (tipo.equals("ID"))
        {
            return String.valueOf(u.getId());
        }
        else if (tipo.equals("Cargo"))
        {
            return u.getRole().name();
        }
        System.err.println("Usuário ou tipo de dado inválido!\n"); 
        return "";
    }
}