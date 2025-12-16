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

    public void atualizarUsuario(String tipo, int id, String dado)
    {
        if (tipo.equals("Nome"))
        {
            usuariodao.atualizarNome(id, dado);
        }
        else if (tipo.equals("Senha"))
        {
            usuariodao.atualizarSenha(id, dado);
        }
        else if (tipo.equals("Cargo"))
        {
            usuariodao.atualizarRole(id, Role.valueOf(dado));
        }
        System.err.println("Usuário ou tipo de dado inválido!\n"); 
    }

    public String obterDado(String tipo, int id)
    {
        if (tipo.equals("Nome"))
        {
            return usuariodao.buscarPorId(id).getUsername();
        }
        else if (tipo.equals("Cargo"))
        {
            return String.valueOf(usuariodao.buscarPorId(id).getRole());
        }
        System.err.println("Usuário ou tipo de dado inválido!\n"); 
        return "";
    }

    public void removerUsuario(int id_u)
    {
        usuariodao.removerUsuario(id_u);
    }
}