package src.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Comentario: Esta classe é responsável por estabelecer uma conexão com um banco de dados MySQL. O método getConnection verifica se a conexão já existe ou está fechada, e se necessário, cria uma nova conexão usando as credenciais fornecidas. Se ocorrer um erro durante a conexão, uma RuntimeException é lançada com uma mensagem de erro detalhada.

public class Conexao {

    private static final String url = "jdbc:mysql://localhost:3306/sistema_filmes?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "sua senha";

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