package src;

public class Avaliacao {

    private int id;
    private int filmeId;
    private int usuarioId;
    private int nota;
    private String comentario;

    public Avaliacao(int filmeId, int usuarioId, int nota, String comentario) {
        this.filmeId = filmeId;
        this.usuarioId = usuarioId;
        this.nota = nota;
        this.comentario = comentario;
    }

    public Avaliacao(int id, int filmeId, int usuarioId, int nota, String comentario) {
        this.id = id;
        this.filmeId = filmeId;
        this.usuarioId = usuarioId;
        this.nota = nota;
        this.comentario = comentario;
    }

    public int getId() {
        return id;
    }

    public int getFilmeId() {
        return filmeId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public int getNota() {
        return nota;
    }

    public String getComentario() {
        return comentario;
    }

    public String estrelas() {
        return "★".repeat(nota) + "☆".repeat(5 - nota);
    }

    @Override
    public String toString() {
        return estrelas() + " | " + comentario;
    }
}