package br.com.alura.challenger.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DadosLivro(
        @JsonAlias("title") String titulo,
        List<DadosAutor> authors,
        @JsonAlias("languages")List<String> idioma,
        @JsonAlias("download_count") Integer downloads
)  {



}

