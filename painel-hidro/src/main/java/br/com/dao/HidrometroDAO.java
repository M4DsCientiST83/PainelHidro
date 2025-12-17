package br.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.database.ConexaoBanco;
import br.com.utilitarios.Hidrometro;
import br.com.utilitarios.RelacaoHidroUser;

public class HidrometroDAO 
{
    public void salvar(Hidrometro hidro) 
    {
        String sql = """
                    INSERT INTO hidrometro (id)
                    VALUES (?)
                """;

        Connection conn = ConexaoBanco.getInstancia().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setInt(1, hidro.getId());

            stmt.executeUpdate();
        } 
        catch (SQLException e) 
        {
            throw new RuntimeException("Erro ao salvar usuário", e);
        }
    }

    public void associarUsuario(int idHidrometro, int idUsuario) 
    {
        String sql = "UPDATE hidrometro SET usuario_id = ? WHERE id = ?";
        Connection conn = ConexaoBanco.getInstancia().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idHidrometro);

            int linhas = stmt.executeUpdate();

            if (linhas == 0) 
            {
                throw new RuntimeException("Hidrômetro não encontrado");
            }
        } 
        catch (SQLException e) 
        {
            throw new RuntimeException("Erro ao associar hidrômetro ao usuário", e);
        }
    }

    public List<RelacaoHidroUser> exibirHidrometrosUsuarios() 
    {
        List<RelacaoHidroUser> lista = new ArrayList<>();

        String sql = "SELECT h.id AS hidrometro, u.nome AS usuario " +
                "FROM hidrometro h " +
                "LEFT JOIN usuario u ON h.usuario_id = u.id " +
                "ORDER BY h.id";

        Connection conn = ConexaoBanco.getInstancia().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) 
        {
            while (rs.next()) 
            {
                int codigo = rs.getInt("hidrometro");
                String nome = rs.getString("usuario");

                lista.add(new RelacaoHidroUser(codigo, nome));
            }
        } 
        catch (SQLException e) 
        {
            throw new RuntimeException("Erro ao buscar hidrômetros", e);
        }
        return lista;
    }

    public void removerHidrometro(int codigoHidrometro) 
    {
        String sql = "DELETE FROM hidrometro WHERE id = ?";

        Connection conn = ConexaoBanco.getInstancia().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setInt(1, codigoHidrometro);

            int linhas = stmt.executeUpdate();

            if (linhas == 0) 
            {
                throw new RuntimeException("Hidrômetro não encontrado");
            }
        } 
        catch (SQLException e) 
        {
            throw new RuntimeException("Erro ao remover hidrômetro", e);
        }
    }

    public List<Hidrometro> buscarPorUsuario(int idUsuario) 
    {
        List<Hidrometro> hidrometros = new ArrayList<>();

        String sql = "SELECT id FROM hidrometro WHERE usuario_id = ? ORDER BY id";

        Connection conn = ConexaoBanco.getInstancia().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setInt(1, idUsuario);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) 
                {
                int id = rs.getInt("id");
                hidrometros.add(new Hidrometro(id));
            }

            return hidrometros;
        } 
        catch (SQLException e) 
        {
            throw new RuntimeException("Erro ao buscar hidrômetros do usuário", e);
        }
    }

    public void atualizarVolumeAcumulado(int idHidrometro, double volume) {
        String sql = "UPDATE hidrometro SET volume_acumulado = ? WHERE id = ?";

        Connection conn = ConexaoBanco.getInstancia().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, volume);
            stmt.setInt(2, idHidrometro);

            int linhas = stmt.executeUpdate();

            if (linhas == 0) {
                throw new RuntimeException("Hidrômetro não encontrado");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar volume do hidrômetro", e);
        }
    }
}