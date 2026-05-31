package src.DAO;

import src.Filme;
import src.conexao.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//comeponente responsável por acessar o banco de dados para operações relacionadas a filmes
public class FilmeDAO {

    public void criar(Filme f) {

        String sql = "INSERT INTO filmes (titulo, diretor, ano, genero) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, f.getTitulo());
            stmt.setString(2, f.getDiretor());
            stmt.setInt(3, f.getAno());
            stmt.setString(4, f.getGenero());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                f.setId(rs.getInt(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Filme> listar() {

        List<Filme> lista = new ArrayList<>();

        String sql = "SELECT * FROM filmes";

        try (Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Filme(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("diretor"),
                        rs.getInt("ano"),
                        rs.getString("genero")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Filme buscarPorId(int id) {

        String sql = "SELECT * FROM filmes WHERE id=?";

        try (Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Filme(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("diretor"),
                        rs.getInt("ano"),
                        rs.getString("genero"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void atualizar(Filme f) {

        String sql = "UPDATE filmes SET titulo=?, diretor=?, ano=?, genero=? WHERE id=?";

        try (Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, f.getTitulo());
            stmt.setString(2, f.getDiretor());
            stmt.setInt(3, f.getAno());
            stmt.setString(4, f.getGenero());
            stmt.setInt(5, f.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletar(int id) {

        String sql = "DELETE FROM filmes WHERE id=?";

        try (Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}