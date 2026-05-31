package src.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String url = "jdbc:mysql://localhost:3306/sistema_filmes?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "Ar@58425873";

    private static Connection conn;

    public static Connection getConnection() {

        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(url, user, password);
            }
            return conn;

        } catch (SQLException e) {
            throw new RuntimeException("Erro de conexão com MySQL", e);
        }
    }
}