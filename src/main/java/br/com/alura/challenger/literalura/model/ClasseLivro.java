package br.com.alura.challenger.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table (name = "livros")
public class ClasseLivro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String tituloLivro;


    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> idioma;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Autor> autores;
    private Integer downloads;

    public ClasseLivro(){

    }

    public ClasseLivro(DadosLivro dadosLivro) {
        this.tituloLivro = dadosLivro.titulo();
        this.autores = dadosLivro.authors().stream()
                .map(d -> new Autor(d.nome(), d.nascimento(), d.falecimento()))
                .collect(Collectors.toList());

        this.idioma = dadosLivro.idioma();
        this.downloads = dadosLivro.downloads();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTituloLivro() {
        return tituloLivro;
    }

    public void setTituloLivro(String tituloLivro) {
        this.tituloLivro = tituloLivro;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<String> getIdioma() {
        return idioma;
    }

    public void setIdioma(List<String> idioma) {
        this.idioma = idioma;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        String nomeAutor = autores != null && !autores.isEmpty() ? autores.getFirst().getNome() : "Autor desconhecido";

        return "----- Livro -----" + "\n" +
                "Titulo: " + tituloLivro + "\n" +
                "Autor: " + nomeAutor + "\n" +
                "Idioma: " + idioma + "\n" +
                "Downloads: " + downloads + "\n" +
                "-----------------";
    }
}
