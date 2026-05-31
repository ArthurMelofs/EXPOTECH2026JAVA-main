//Comentario: Esta classe é responsável por gerar um hash SHA-256 a partir de uma string de senha. O método sha256 recebe uma string, converte-a em bytes, gera o hash e retorna o resultado como uma string hexadecimal.
package src;

import java.security.MessageDigest;

public class HashUtil {

    public static String sha256(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(senha.getBytes("UTF-8"));

            StringBuilder sb = new StringBuilder();

            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar hash", e);
        }
    }
}