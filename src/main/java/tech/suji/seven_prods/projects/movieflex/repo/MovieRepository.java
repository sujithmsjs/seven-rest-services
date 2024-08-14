package tech.suji.seven_prods.projects.movieflex.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tech.suji.seven_prods.projects.movieflex.dto.Genre;
import tech.suji.seven_prods.projects.movieflex.entity.Movie;


public interface MovieRepository extends JpaRepository<Movie, Long> {

    boolean existsByTitleIgnoreCase(String title);


    @Query("SELECT DISTINCT m.genre FROM Movie m")
    List<Genre> findDistinctGenres();

}
