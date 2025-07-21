package br.com.alura.Challenge_Literalura;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Principal {
    Scanner leitura = new Scanner(System.in);




    public void exibeMenu(){

        var opcao  = -1;
        while(opcao != 0){
            var menu = """ 
               
                1- buscar livro pelo título
                2- listar livros registrados
                3- listar autores registrados
                4- listar autores vivos em um determinado ano
                5- listar livros em um determinado idioma
                0- Sair
                
                """;
            System.out.println(menu);
            opcao = leitura.nextInt();

            switch(opcao){
                case 1:
                    buscaLivrosPeloTitulo();
                    break;
                case 2:
                    ListaLivrosRegistrados();
                    break;
                case 3:
                    ListaAutoresRegistrados();
                    break;
                case 4:
                    ListaAutoresVivosAno();
                    break;
                case 5:
                    ListaLivrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Obrigado por ter vindo! \n");
                    break;
            }
        }
        }

    private void buscaLivrosPeloTitulo() {
        System.out.println("Insira o nome do livro que você deseja procurar");

    }

    private void ListaLivrosRegistrados() {
    }

    private void ListaAutoresRegistrados() {
    }

    private void ListaAutoresVivosAno() {
        System.out.println("Insira o ano que deseja pesquisar");
    }

    private void ListaLivrosPorIdioma() {
        System.out.println("""
                Insira o idioma para realizar a busca:
                es- espanhol
                en- inglês
                fr- francês
                pt- português
                
                """);
    }


}
