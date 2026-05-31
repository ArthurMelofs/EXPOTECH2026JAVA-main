package src.DAO;

import src.Avaliacao;
import src.conexao.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//Componente responsável por acessar o banco de dados para operações relacionadas a avaliações
public class AvaliacaoDAO {

    public void salvar(Avaliacao a) {

        String sql = "INSERT INTO avaliacoes (filme_id, usuario_id, nota, comentario) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, a.getFilmeId());
            stmt.setInt(2, a.getUsuarioId());
            stmt.setInt(3, a.getNota());
            stmt.setString(4, a.getComentario());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Avaliacao> listarPorFilme(int filmeId) {

        List<Avaliacao> lista = new ArrayList<>();

        String sql = "SELECT * FROM avaliacoes WHERE filme_id=?";

        try (Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, filmeId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new Avaliacao(
                        rs.getInt("id"),
                        rs.getInt("filme_id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("nota"),
                        rs.getString("comentario")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}