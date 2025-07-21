package br.com.alura.Challenge_Literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultadoAPI(List<DadosLivros> results) {}
