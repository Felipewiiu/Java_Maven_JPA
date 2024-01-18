package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(int totalTemporada, double avaliacao);

    @Query("select s from Serie s WHERE s.totalTemporadas <= :totalTemporada AND s.avaliacao >= :avaliacao")
    // na linguagem JQPL os dois pontos(:) indica o uso de parâmetros em uma query
    List<Serie> seriesPorTemporadasEAvaliacao(int totalTemporada, double avaliacao);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:trechoEpsodio%")
    List<Episodio> epsodiosPorTrecho(String trechoEpsodio);
    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT 5 ")
    List<Episodio> topEpisodioPorSerie(Serie serie);
@Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie AND YEAR(e.dataLancamento) >= :anoLancamento")
    List<Episodio> episodiosPorSerieEPorano(Serie serie, int anoLancamento);
}

