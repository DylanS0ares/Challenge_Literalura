package br.com.alura.challenger.literalura.principal;

import br.com.alura.challenger.literalura.model.*;
import br.com.alura.challenger.literalura.repository.LivroRepository;
import br.com.alura.challenger.literalura.service.ConsumoApi;
import br.com.alura.challenger.literalura.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "http://gutendex.com/books/?search=";
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private List<DadosLivro> dadosLivros = new ArrayList<>();


    private LivroRepository repositorio;

    public Principal(LivroRepository repositorio) {
        this.repositorio = repositorio;
    }


    public void exibeMenu() {

        var opcao = -1;
        while (opcao != 0) {

            var menu = """
                    1 - Buscar livro pelo titulo
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinados ano
                    5 - Listar livros em um determinado idioma
                    
                    0 - Sair 
                    """;

            System.out.println(menu);
            String linha;
            linha = leitura.nextLine();
            try {
                opcao = Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, digite um número.");
                continue;
            }

            switch (opcao) {
                case 1:
                    buscarLivroWeb();
                    break;
                case 2:
                    listaLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLivrosIdioma();
                    break;
                default:
                    System.out.println("Opção invalida");
            }


        }
    }



    private void buscarLivroWeb() {
        System.out.println("Digite o nome do livro para busca: ");
        var nomeLivro = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "+"));
        DadosApi dadosApi = conversor.obterDados(json, DadosApi.class);
        List<DadosLivro> livros = dadosApi.results();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
            return;
        }

        DadosLivro livro = livros.get(0);
        ClasseLivro livroEntity = new ClasseLivro(livro);


        System.out.println("\n----- LIVRO ENCONTRADO -----");
        System.out.println("Título: " + livroEntity.getTituloLivro());
        System.out.println("Autor: " + livroEntity.getAutores().getFirst().getNome());
        System.out.println("Idioma: " + String.join(", ", livroEntity.getIdioma()));
        System.out.println("Downloads: " + livroEntity.getDownloads());
        System.out.println("-----------------------------\n");

        try {
            repositorio.save(livroEntity);
            System.out.println("Livro salvo com sucesso!");
        } catch (DataIntegrityViolationException e) {
            System.out.println("Livro já está salvo no banco de dados.");
        }




    }

    private void listaLivrosRegistrados() {

        List<ClasseLivro> livros = repositorio.findAll();
        livros.forEach(livro -> {
            System.out.println("----- Livro -----");
            System.out.println("Título: " + livro.getTituloLivro());

            if (!livro.getAutores().isEmpty()) {
                System.out.println("Autor: " + livro.getAutores().get(0).getNome());
            }

            if (!livro.getIdioma().isEmpty()) {
                System.out.println("Idioma: " + String.join(", ", livro.getIdioma()));
            }

            System.out.println("Downloads: " + livro.getDownloads());
            System.out.println("-----------------");
        });
    }

    private void listarAutoresRegistrados() {
        List<ClasseLivro> livros = repositorio.findAll();

        if (livros.isEmpty()) {
            System.out.println("Nenhum autor registrado.");
            return;
        }


        livros.stream()
                .flatMap(livro -> livro.getAutores().stream()
                        .map(autor -> new Object() {
                            String nome = autor.getNome();
                            Integer nascimento = autor.getNascimento();
                            Integer falecimento = autor.getFalecimento();
                            String tituloLivro = livro.getTituloLivro();
                        })
                )
                .collect(Collectors.groupingBy(
                        a -> a.nome + "|" + a.nascimento + "|" + a.falecimento,
                        Collectors.mapping(a -> a.tituloLivro, Collectors.toList())
                ))
                .forEach((chave, titulos) -> {
                    String[] partes = chave.split("\\|");
                    String nome = partes[0];
                    String nascimento = partes[1];
                    String falecimento = partes[2];

                    System.out.println("Autor: " + nome);
                    System.out.println("Nascimento: " + nascimento);
                    System.out.println("Falecimento: " + falecimento);
                    System.out.println("Livros: " + String.join(", ", titulos));
                    System.out.println("----------------------------");
                });



    }

    private void listarAutoresVivos() {
        System.out.print("Qual o ano que o autor estava vivo? ");
        int ano = leitura.nextInt();
        leitura.nextLine();

        List<ClasseLivro> livros = repositorio.findAll();

        // Mapeia autores vivos no ano e associa seus livros
        Map<Autor, List<ClasseLivro>> autoresVivos = new HashMap<>();

        for (ClasseLivro livro : livros) {
            for (Autor autor : livro.getAutores()) {
                Integer nascimento = autor.getNascimento();
                Integer falecimento = autor.getFalecimento();

                if (nascimento != null && falecimento != null &&
                        nascimento <= ano && falecimento >= ano) {

                    autoresVivos
                            .computeIfAbsent(autor, k -> new ArrayList<>())
                            .add(livro);
                }
            }
        }

        // Imprime o resultado
        if (autoresVivos.isEmpty()) {
            System.out.println("Nenhum autor encontrado vivo nesse ano.");
        } else {
            for (Map.Entry<Autor, List<ClasseLivro>> entry : autoresVivos.entrySet()) {
                Autor autor = entry.getKey();
                List<ClasseLivro> livrosDoAutor = entry.getValue();

                System.out.println("Nome: " + autor.getNome());
                System.out.println("Nascimento: " + autor.getNascimento());
                System.out.println("Falecimento: " + autor.getFalecimento());

                String livrosFormatados = livrosDoAutor.stream()
                        .map(ClasseLivro::getTituloLivro)
                        .distinct()
                        .collect(Collectors.joining(", "));

                System.out.println("Livros: " + livrosFormatados);
                System.out.println("-------------");
            }
        }
    }

    private void listarLivrosIdioma() {
        List<ClasseLivro> livros = repositorio.findAll();

        if (livros.isEmpty()){
            System.out.println("Nenhum livro registrado.");
        }
        System.out.println("""
                    Insira um idioma para busca:
                    pt - portugues
                    es - espanhol
                    en - ingles
                    fr - frances
                    """);
        String idioma = leitura.nextLine().trim().toLowerCase();

        List<ClasseLivro> livroFiltrado = livros.stream()
                .filter(l -> l.getIdioma().contains(idioma))
                .toList();


        if (livroFiltrado.isEmpty()){
            System.out.println("Nenhum livro no idioma '" + idioma + "' encontrado");
            return;
        }
        livros.stream()
                .filter(livro -> livro.getIdioma().contains(idioma))
                .forEach(livro -> {
                    System.out.println("---- LIVRO NO IDIOMA " + idioma +" ----");
                    System.out.println("Titulo: " + livro.getTituloLivro());
                    System.out.println("Autor: " + livro.getAutores().getFirst().getNome());
                    System.out.println("Idioma: " + livro.getIdioma());
                    System.out.println("Downloads: " + livro.getDownloads());
                    System.out.println("----------------\n");
                });
    }


}








