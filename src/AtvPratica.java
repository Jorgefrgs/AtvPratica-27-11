import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AtvPratica {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        List<Livros> biblioteca = new ArrayList<>();
        List<Usuarios> listUsuarios = new ArrayList<>();
        boolean continuar = true;

        while (continuar) {
            System.out.println("Menu: ");
            System.out.println("1. Adicionar usuário\n2. Listar usuários\n3. Adicionar Livro" +
                    "\n4. Remover Livros\n5. Listar Livros\n6. Buscar Livros\n7. Emprestar Livro\n8. Devolver Livro\n9. Sair");
            System.out.print("Escolha uma opção: ");
            int op = scan.nextInt();
            scan.nextLine();

            switch (op) {
                case 1 -> {
                    System.out.print("Digite o nome do usuário: ");
                    String nomeUsuario = scan.nextLine();
                    System.out.print("Digite o id: ");
                    int id = scan.nextInt();
                    scan.nextLine();
                    Usuarios usuario = new Usuarios(nomeUsuario, id);
                    listUsuarios.add(usuario);
                    System.out.println("Usuário cadastrado com sucesso!");
                }
                case 2 -> {
                    System.out.println("Lista de Usuários:");
                    if (listUsuarios.isEmpty()) {
                        System.out.println("Nenhum usuário cadastrado.");
                    } else {
                        for (Usuarios usuario : listUsuarios) {
                            System.out.println("Nome: " + usuario.nome + " | ID: " + usuario.id);
                        }
                    }
                }
                case 3 -> {
                    System.out.print("Digite o título do livro: ");
                    String titulo = scan.nextLine();
                    System.out.print("Digite o autor do livro: ");
                    String autor = scan.nextLine();
                    System.out.print("Digite o ano de publicação: ");
                    int anoPublicacao = scan.nextInt();
                    scan.nextLine();
                    Livros livro = new Livros(titulo, autor, anoPublicacao, true);
                    biblioteca.add(livro);
                    System.out.println("Livro cadastrado com sucesso!");
                }
                case 4 -> {
                    System.out.print("Digite o título do livro a ser removido: ");
                    String nome = scan.nextLine();
                    boolean encontrado = false;
                    for (Livros livro : new ArrayList<>(biblioteca)) {
                        if (livro.titulo.equalsIgnoreCase(nome)) {
                            biblioteca.remove(livro);
                            System.out.println("Livro removido com sucesso!");
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        System.out.println("Livro não encontrado.");
                    }
                }
                case 5 -> {
                    System.out.println("Lista de Livros:");
                    if (biblioteca.isEmpty()) {
                        System.out.println("Nenhum livro cadastrado.");
                    } else {
                        for (Livros livro : biblioteca) {
                            System.out.println("Título: " + livro.titulo + " | Autor: " + livro.autor +
                                    " | Ano: " + livro.anoPublicacao + " | Disponível: " + (livro.disponivel ? "Sim" : "Não"));
                        }
                    }
                }
                case 6 -> {
                    System.out.print("Digite o título do livro para buscar: ");
                    String titulo = scan.nextLine();
                    boolean encontrado = false;
                    for (Livros livro : biblioteca) {
                        if (livro.titulo.equalsIgnoreCase(titulo)) {
                            System.out.println("Informações do Livro:");
                            System.out.println("Título: " + livro.titulo + " | Autor: " + livro.autor +
                                    " | Ano: " + livro.anoPublicacao + " | Disponível: " + (livro.disponivel ? "Sim" : "Não"));
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        System.out.println("Livro não encontrado.");
                    }
                }
                case 7 -> {
                    System.out.print("Qual o nome do usuário que deseja pegar o livro: ");
                    String nome = scan.nextLine();
                    System.out.print("Qual livro será emprestado: ");
                    String livroNome = scan.nextLine();
                    Usuarios usuario = null;
                    Livros livro = null;

                    for (Usuarios u : listUsuarios) {
                        if (u.nome.equalsIgnoreCase(nome)) {
                            usuario = u;
                            break;
                        }
                    }
                    for (Livros l : biblioteca) {
                        if (l.titulo.equalsIgnoreCase(livroNome)) {
                            livro = l;
                            break;
                        }
                    }
                    if (usuario == null) {
                        System.out.println("Usuário não encontrado.");
                    } else if (livro == null) {
                        System.out.println("Livro não encontrado.");
                    } else if (!livro.disponivel) {
                        System.out.println("Livro indisponível para empréstimo.");
                    } else {
                        livro.disponivel = false;
                        usuario.emprestados.add(livro);
                        System.out.println("Livro '" + livroNome + "' emprestado para " + nome);
                    }
                }
                case 8 -> {
                    System.out.print("Qual o nome do usuário: ");
                    String nomeUsuario = scan.nextLine();
                    System.out.print("Qual o nome do livro a ser devolvido: ");
                    String nomeLivro = scan.nextLine();

                    Usuarios usuario = null;
                    Livros livro = null;

                    for (Usuarios u : listUsuarios) {
                        if (u.nome.equalsIgnoreCase(nomeUsuario)) {
                            usuario = u;
                            break;
                        }
                    }
                    if (usuario != null) {
                        for (Livros l : usuario.emprestados) {
                            if (l.titulo.equalsIgnoreCase(nomeLivro)) {
                                livro = l;
                                break;
                            }
                        }
                        if (livro != null) {
                            usuario.emprestados.remove(livro);
                            livro.disponivel = true;
                            System.out.println("Livro '" + nomeLivro + "' devolvido com sucesso.");
                        } else {
                            System.out.println("O usuário não possui este livro emprestado.");
                        }
                    } else {
                        System.out.println("Usuário não encontrado.");
                    }
                }
                case 9 -> {
                    System.out.println("Saindo...");
                    continuar = false;
                }
                default -> System.out.println("Insira um valor válido.");
            }
        }
    }

    static class Livros {
        String titulo;
        String autor;
        int anoPublicacao;
        boolean disponivel;

        public Livros(String titulo, String autor, int anoPublicacao, boolean disponivel) {
            this.titulo = titulo;
            this.autor = autor;
            this.anoPublicacao = anoPublicacao;
            this.disponivel = disponivel;
        }
    }

    static class Usuarios {
        String nome;
        int id;
        List<Livros> emprestados = new ArrayList<>();

        public Usuarios(String nome, int id) {
            this.nome = nome;
            this.id = id;
        }
    }
}
