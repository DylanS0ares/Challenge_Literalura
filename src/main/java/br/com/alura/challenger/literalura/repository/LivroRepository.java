package br.com.alura.challenger.literalura.repository;

import br.com.alura.challenger.literalura.model.ClasseLivro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<ClasseLivro, Long> {
}
