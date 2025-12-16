package br.com.dao;

import br.com.database.ConexaoBanco;
import br.com.Usuario;
import br.com.utilitarios.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO 
{
    public void salvar(Usuario usuario) 
    {

        String sql = """
            INSERT INTO usuario (id, nome, senha, role)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setInt(1, usuario.getId());
            stmt.setString(2, usuario.getUsername());
            stmt.setString(3, usuario.getPasswordHash());
            stmt.setString(4, usuario.getRole().name());

            stmt.executeUpdate();
        } 
        catch (SQLException e) 
        {
            throw new RuntimeException("Erro ao salvar usuário", e);
        }
    }

    public List<Usuario> retornarTodos() 
    {
        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT id, nome, senha, role FROM usuario";

        try (Connection conn = ConexaoBanco.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) 
            {
                Usuario u = new Usuario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("senha")
                );

                u.setRole(Role.valueOf(rs.getString("role")));

                usuarios.add(u);
            }

        } 
        catch (Exception e) 
        {
            throw new RuntimeException(e);
        }

        return usuarios;
    }

    public Usuario autenticar(String nome, String senha) 
    {
        String sql = "SELECT id, nome, senha, role FROM usuario WHERE nome = ? AND senha = ?";

        try (Connection conn = ConexaoBanco.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) 
            {
                Usuario u = new Usuario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("senha")
                );

                u.setRole(Role.valueOf(rs.getString("role")));
                return u;
            }

            return null; 

        } 
        catch (Exception e) 
        {
            throw new RuntimeException("Erro ao autenticar usuário", e);
        }
    }
}
