package br.com.alura.challenger.literalura.service;

import java.util.List;

public interface iConverteDados {
   <T> T obterDados (String json , Class <T> classe);

}
