package tech.suji.seven_prods.projects.movieflex.service;

import java.util.List;

import tech.suji.seven_prods.projects.movieflex.dto.Genre;
import tech.suji.seven_prods.projects.movieflex.dto.MovieDTO;
import tech.suji.seven_prods.projects.movieflex.dto.MovieSearchDTO;

public interface MovieService {

	boolean titleExists(String title);

	Object searchMovies(MovieSearchDTO searchDTO);

	void delete(Long id);

	void update(Long id, MovieDTO movieDTO);

	Long create(MovieDTO movieDTO);

	MovieDTO get(Long id);

	List<MovieDTO> findAll();

	boolean existsByMovieName(String movieName);

	List<Genre> getUniqueGenres();


}
