package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    // O JpaRepository recebe dois valores que é o tipo e o ID no caso o tipo do ID é do tipo Long

    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

//    List<Serie> findByAtoresContainingIgnoreCase(String nomeAtor);
    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, Double avaliacao);

    List<Serie> findTop3ByOrderByAvaliacaoDesc();
    // aqui realizamos uma projeção

    List<Serie> findByGenero(Categoria categoria);
}
