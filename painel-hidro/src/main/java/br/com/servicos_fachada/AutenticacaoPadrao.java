package br.com.servicos_fachada;

import br.com.dao.UsuarioDAO;
import br.com.factory.Usuario;

public class AutenticacaoPadrao extends AutenticacaoTemplate 
{
    private UsuarioDAO usuarioDAO;

    public AutenticacaoPadrao() 
    {
        this.usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected boolean validarCredenciais(String username, String senha) 
    {
        if (username == null || username.trim().isEmpty()) 
        {
            System.out.println("Erro: Nome de usuário não pode ser vazio");
            return false;
        }

        if (senha == null || senha.trim().isEmpty()) 
        {
            System.out.println("Erro: Senha não pode ser vazia");
            return false;
        }

        return true;
    }

    @Override
    protected Usuario buscarUsuario(String username, String senha) 
    {
        try 
        {
            Usuario usuario = usuarioDAO.autenticar(username, senha);

            if (usuario == null) 
            {
                System.out.println("Erro: Usuário ou senha inválidos");
            }

            return usuario;
        } 
        catch (Exception e) 
        {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
            return null;
        }
    }

    @Override
    protected boolean verificarAutorizacao(Usuario usuario) 
    {
        if (usuario.getRole() == null) 
        {
            System.out.println("Erro: Usuário sem permissão definida");
            return false;
        }

        return true;
    }
}
