package src;

import java.util.List;
import java.util.Scanner;

import src.DAO.UsuarioDAO;
import src.DAO.FilmeDAO;
import src.DAO.AvaliacaoDAO;

public class SistemaFilmes {

    static Scanner scanner = new Scanner(System.in);

    // usuário logado no sistema
    static Usuario usuarioLogado = null;

    // DAOs (camada de persistência)
    static UsuarioDAO usuarioDAO = new UsuarioDAO();
    static FilmeDAO filmeDAO = new FilmeDAO();
    static AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();

    public static void main(String[] args) {
        telaInicial();
        scanner.close();
    }

    // =========================================================
    // TELA INICIAL
    // =========================================================

    static void telaInicial() {

        int opcao;

        do {

            System.out.println("\n=================================");
            System.out.println("     SISTEMA DE FILMES");
            System.out.println("=================================");
            System.out.println("1. Fazer Login");
            System.out.println("2. Criar Conta");
            System.out.println("0. Sair");
            System.out.println("=================================");

            opcao = lerInteiro("Escolha: ");

            switch (opcao) {

                case 1 -> login();
                case 2 -> cadastrarUsuario();
                case 0 -> System.out.println("Encerrando sistema...");
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    // =========================================================
    // LOGIN
    // =========================================================

    static void login() {

        System.out.println("\n======= LOGIN =======");

        System.out.print("Usuário: ");
        String nome = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        // hash obrigatório (compatível com banco)
        String senhaHash = HashUtil.sha256(senha);

        // autenticação via banco (DAO)
        usuarioLogado = usuarioDAO.login(nome, senhaHash);

        if (usuarioLogado != null) {
            System.out.println("\nLogin realizado com sucesso!");
            menuPrincipal();
        } else {
            System.out.println("Usuário ou senha incorretos.");
        }
    }

    // =========================================================
    // CADASTRO
    // =========================================================

    static void cadastrarUsuario() {

        System.out.println("\n======= CADASTRO =======");

        System.out.print("Novo usuário: ");
        String nome = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Usuario usuario = new Usuario();
        usuario.setNome(nome);

        // senha nunca salva em texto puro
        usuario.setSenha(HashUtil.sha256(senha));

        usuario.setPerfil("USUARIO");

        usuarioDAO.cadastrarUsuario(usuario);

        System.out.println("Conta criada com sucesso!");
    }

    // =========================================================
    // MENU PRINCIPAL
    // =========================================================

    static void menuPrincipal() {

        if (usuarioLogado.isAdmin())
            menuAdministrador();
        else
            menuUsuario();
    }

    // =========================================================
    // ADMIN
    // =========================================================

    static void menuAdministrador() {

        int opcao;

        do {

            System.out.println("\n=================================");
            System.out.println("ADMINISTRADOR - " + usuarioLogado.getNome());
            System.out.println("=================================");
            System.out.println("1. Listar Filmes");
            System.out.println("2. Adicionar Filme");
            System.out.println("3. Editar Filme");
            System.out.println("4. Excluir Filme");
            System.out.println("5. Ver Avaliações");
            System.out.println("0. Logout");
            System.out.println("=================================");

            opcao = lerInteiro("Escolha: ");

            switch (opcao) {

                case 1 -> listarFilmes();
                case 2 -> cadastrarFilme();
                case 3 -> editarFilme();
                case 4 -> excluirFilme();
                case 5 -> verAvaliacoes();
                case 0 -> usuarioLogado = null;
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    // =========================================================
    // USUÁRIO
    // =========================================================

    static void menuUsuario() {

        int opcao;

        do {

            System.out.println("\n=================================");
            System.out.println("USUÁRIO - " + usuarioLogado.getNome());
            System.out.println("=================================");
            System.out.println("1. Ver Filmes");
            System.out.println("2. Avaliar Filme");
            System.out.println("0. Logout");
            System.out.println("=================================");

            opcao = lerInteiro("Escolha: ");

            switch (opcao) {

                case 1 -> listarFilmes();
                case 2 -> avaliarFilme();
                case 0 -> usuarioLogado = null;
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    // =========================================================
    // FILMES
    // =========================================================

    static void listarFilmes() {

        List<Filme> filmes = filmeDAO.listar();

        System.out.println("\n======= FILMES =======");

        for (Filme f : filmes) {

            double media = calcularMedia(f.getId());

            String mediaTexto = media > 0
                    ? String.format("%.1f/5", media)
                    : "Sem avaliações";

            System.out.println(f + " | Média: " + mediaTexto);
        }
    }

    static void cadastrarFilme() {

        System.out.println("\n======= CADASTRAR FILME =======");

        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Diretor: ");
        String diretor = scanner.nextLine();

        int ano = lerInteiro("Ano: ");

        System.out.print("Gênero: ");
        String genero = scanner.nextLine();

        Filme f = new Filme(0, titulo, diretor, ano, genero);

        filmeDAO.criar(f);

        System.out.println("Filme cadastrado com sucesso!");
    }

    static void editarFilme() {

        listarFilmes();

        int id = lerInteiro("\nDigite o ID do filme: ");

        Filme f = filmeDAO.buscarPorId(id);

        if (f == null) {
            System.out.println("Filme não encontrado.");
            return;
        }

        System.out.print("Novo título: ");
        f.setTitulo(scanner.nextLine());

        System.out.print("Novo diretor: ");
        f.setDiretor(scanner.nextLine());

        f.setAno(lerInteiro("Novo ano: "));

        System.out.print("Novo gênero: ");
        f.setGenero(scanner.nextLine());

        filmeDAO.atualizar(f);

        System.out.println("Filme atualizado com sucesso!");
    }

    static void excluirFilme() {

        listarFilmes();

        int id = lerInteiro("\nDigite o ID do filme: ");

        filmeDAO.deletar(id);

        System.out.println("Filme excluído com sucesso!");
    }

    // =========================================================
    // AVALIAÇÃO
    // =========================================================

    static void avaliarFilme() {

        listarFilmes();

        int filmeId = lerInteiro("\nDigite o ID do filme: ");

        int nota;

        do {
            nota = lerInteiro("Nota (1-5): ");
        } while (nota < 1 || nota > 5);

        System.out.print("Comentário: ");
        String comentario = scanner.nextLine();

        // CORREÇÃO PRINCIPAL:
        // NÃO usar ID manual nem nome de usuário
        Avaliacao a = new Avaliacao(
                0,
                filmeId,
                usuarioLogado.getId(),
                nota,
                comentario);

        avaliacaoDAO.salvar(a);

        System.out.println("Avaliação enviada.");
    }

    static void verAvaliacoes() {

        listarFilmes();

        int filmeId = lerInteiro("\nDigite o ID do filme: ");

        List<Avaliacao> lista = avaliacaoDAO.listarPorFilme(filmeId);

        System.out.println("\n======= AVALIAÇÕES =======");

        if (lista.isEmpty()) {
            System.out.println("Sem avaliações.");
            return;
        }

        for (Avaliacao a : lista) {
            System.out.println(a);
        }
    }

    // =========================================================
    // MÉDIA
    // =========================================================

    static double calcularMedia(int filmeId) {

        List<Avaliacao> lista = avaliacaoDAO.listarPorFilme(filmeId);

        if (lista.isEmpty())
            return 0;

        int soma = 0;

        for (Avaliacao a : lista) {
            soma += a.getNota();
        }

        return (double) soma / lista.size();
    }

    // =========================================================
    // INPUT
    // =========================================================

    static int lerInteiro(String msg) {

        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Digite um número válido.");
            }
        }
    }
}