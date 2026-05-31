package src.DAO;

import src.Usuario;
import src.conexao.Conexao;

import java.sql.*;

//Componente responsável por acessar o banco de dados para operações relacionadas a usuários
public class UsuarioDAO {

    public void cadastrarUsuario(Usuario u) {

        String sql = "INSERT INTO usuarios (nome, senha, perfil) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getSenha());
            stmt.setString(3, u.getPerfil());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Usuario login(String nome, String senha) {

        String sql = "SELECT * FROM usuarios WHERE nome=? AND senha=?";

        try (Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setSenha(rs.getString("senha"));
                u.setPerfil(rs.getString("perfil"));
                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}