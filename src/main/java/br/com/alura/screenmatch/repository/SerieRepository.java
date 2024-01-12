package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    // O JpaRepository recebe dois valores que é o tipo e o ID no caso o tipo do ID é do tipo Long
}
