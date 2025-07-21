package br.com.alura.Challenge_Literalura.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
