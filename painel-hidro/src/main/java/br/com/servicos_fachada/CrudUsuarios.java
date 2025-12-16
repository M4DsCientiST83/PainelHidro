package br.com.servicos_fachada;

import br.com.Usuario;
import br.com.dao.UsuarioDAO;
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
}