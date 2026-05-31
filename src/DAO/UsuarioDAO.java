package src.DAO;

import src.Usuario;
import src.HashUtil;
import src.conexao.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    public void cadastrarUsuario(Usuario u) {

        String sql = "INSERT INTO usuarios (nome, senha, perfil) VALUES (?, ?, ?)";

        try (
                Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getSenha());
            stmt.setString(3, u.getPerfil());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para autenticar um usuário com base no nome e senha fornecidos

    public Usuario login(String nome, String senha) {

        String sql = "SELECT * FROM usuarios WHERE nome = ?";

        try (
                Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                String senhaBanco = rs.getString("senha");
                String senhaHash = HashUtil.sha256(senha);

                if (senhaBanco.equals(senhaHash)) {

                    Usuario u = new Usuario();

                    u.setId(rs.getInt("id"));
                    u.setNome(rs.getString("nome"));
                    u.setSenha(rs.getString("senha"));
                    u.setPerfil(rs.getString("perfil"));

                    return u;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}