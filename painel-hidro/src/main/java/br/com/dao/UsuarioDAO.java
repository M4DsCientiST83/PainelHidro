package br.com.dao;

import br.com.database.ConexaoBanco;
import br.com.utilitarios.Role;
import br.com.utilitarios.Usuario;

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

    public Usuario buscarPorId(int id) 
    {
        String sql = "SELECT id, nome, senha, role FROM usuario WHERE id = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setInt(1, id);
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
        catch (SQLException e) 
        {
            throw new RuntimeException("Erro ao buscar usuário por ID", e);
        }
    }

    public void atualizarRole(int idUsuario, Role novaRole) 
    {
        String sql = "UPDATE usuario SET role = ? WHERE id = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setString(1, novaRole.name());
            stmt.setInt(2, idUsuario);

            stmt.executeUpdate();

        } 
        catch (SQLException e) 
        {
            throw new RuntimeException("Erro ao atualizar role", e);
        }
    }

    public void atualizarSenha(int idUsuario, String novaSenha) 
    {
        String sql = "UPDATE usuario SET senha = ? WHERE id = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setString(1, novaSenha);
            stmt.setInt(2, idUsuario);

            stmt.executeUpdate();
        } 
        catch (SQLException e) 
        {
            throw new RuntimeException("Erro ao atualizar senha", e);
        }
    }

    public void atualizarNome(int idUsuario, String novoNome) 
    {
        String sql = "UPDATE usuario SET nome = ? WHERE id = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoNome);
            stmt.setInt(2, idUsuario);

            stmt.executeUpdate();

        } 
        catch (SQLException e) 
        {
            throw new RuntimeException("Erro ao atualizar nome", e);
        }
    }

    public void removerUsuario(int idUsuario) 
    {
        String desassociarHidrometros =
            "UPDATE hidrometro SET usuario_id = NULL WHERE usuario_id = ?";

        String removerUsuario =
            "DELETE FROM usuario WHERE id = ?";

        try (Connection conn = ConexaoBanco.conectar()) 
        {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt1 = conn.prepareStatement(desassociarHidrometros);
                PreparedStatement stmt2 = conn.prepareStatement(removerUsuario)) 
            {
                stmt1.setInt(1, idUsuario);
                stmt1.executeUpdate();

                stmt2.setInt(1, idUsuario);
                int linhas = stmt2.executeUpdate();

                if (linhas == 0) 
                {
                    throw new RuntimeException("Usuário não encontrado");
                }

                conn.commit();

            } 
            catch (Exception e) 
            {
                conn.rollback();
                throw e;
            }

        } 
        catch (SQLException e) 
        {
            throw new RuntimeException("Erro ao remover usuário", e);
        }
    }

}
