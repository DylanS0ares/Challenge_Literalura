package br.com.alura.Challenge_Literalura;

import br.com.alura.Challenge_Literalura.model.DadosLivros;
import br.com.alura.Challenge_Literalura.model.ResultadoAPI;
import br.com.alura.Challenge_Literalura.service.ConsumoAPI;
import br.com.alura.Challenge_Literalura.service.ConverteDados;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private List<DadosLivros> dadosLivros = new ArrayList<>();

    public void exibeMenu() {
        int opcao = -1;
        while (opcao != 0) {
            var menu = """
                    
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    0 - Sair
                    
                    """;
            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1 -> buscaLivrosPeloTitulo();
                case 2 -> listarLivrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivosAno();
                case 5 -> listarLivrosPorIdioma();
                case 0 -> System.out.println("Obrigado por ter vindo!");
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void buscaLivrosPeloTitulo() {
        System.out.println("Insira o nome do livro que você deseja procurar:");
        String busca = leitura.nextLine();
        String buscaCodificada = URLEncoder.encode(busca, StandardCharsets.UTF_8);

        var json = consumo.obterDados(ENDERECO + buscaCodificada);
        ResultadoAPI resultado = conversor.obterDados(json, ResultadoAPI.class);
        List<DadosLivros> livros = resultado.results();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
            return;
        }

        DadosLivros livro = livros.get(0);

        System.out.println("----- LIVRO -----");
        System.out.println("Título: " + livro.titulo());
        System.out.println("Autor: " + livro.autor().get(0).name());
        System.out.println("Idioma: " + livro.idiomas());
        System.out.println("Número de downloads: " + livro.downloads());
        System.out.println("----------------");


        dadosLivros.add(livro);
    }

    private void listarLivrosRegistrados() {
        if (dadosLivros.isEmpty()) {
            System.out.println("Nenhum livro registrado ainda.");
            return;
        }

        System.out.println("----- LIVROS REGISTRADOS -----");
        dadosLivros.forEach(livro -> {
            System.out.println("Título: " + livro.titulo());
            System.out.println("Autor: " + livro.autor().getFirst().name());
            System.out.println("Idioma: " + livro.idiomas());
            System.out.println("Downloads: " + livro.downloads());
            System.out.println("----------------");
        });
    }

    private void listarAutoresRegistrados() {
        if (dadosLivros.isEmpty()) {
            System.out.println("Nenhum autor registrado ainda.");
            return;
        }

        System.out.println("----- AUTORES REGISTRADOS -----");
        dadosLivros.stream()
                .flatMap(l -> l.autor().stream())
                .map(a -> a.name())
                .distinct()
                .forEach(System.out::println);
    }

    private void listarAutoresVivosAno() {
        System.out.println("Insira o ano que deseja pesquisar:");
        int ano = leitura.nextInt();
        leitura.nextLine();

        System.out.println("----- AUTORES VIVOS EM " + ano + " -----");
        dadosLivros.stream()
                .flatMap(l -> l.autor().stream())
                .filter(a -> a.birth_year() != null && a.death_year() != null)
                .filter(a -> a.birth_year() <= ano && a.death_year() >= ano)
                .map(a -> a.name())
                .distinct()
                .forEach(System.out::println);
    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
                Insira o idioma para realizar a busca:
                es - Espanhol
                en - Inglês
                fr - Francês
                pt - Português
                """);
        String idioma = leitura.nextLine();

        System.out.println("----- LIVROS NO IDIOMA '" + idioma + "' -----");
        dadosLivros.stream()
                .filter(l -> l.idiomas().contains(idioma))
                .forEach(l -> {
                    System.out.println("Título: " + l.titulo());
                    System.out.println("Autor: " + l.autor().getFirst().name());
                    System.out.println("Idioma: " + l.idiomas());
                    System.out.println("Downloads: " + l.downloads());
                    System.out.println("----------------");
                });
    }
}
