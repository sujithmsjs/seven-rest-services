package tech.suji.seven_prods.projects.movieflex.service_impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import tech.suji.seven_prods.projects.movieflex.dto.Genre;
import tech.suji.seven_prods.projects.movieflex.dto.MovieDTO;
import tech.suji.seven_prods.projects.movieflex.dto.MovieSearchDTO;
import tech.suji.seven_prods.projects.movieflex.entity.Movie;
import tech.suji.seven_prods.projects.movieflex.repo.MovieRepository;
import tech.suji.seven_prods.projects.movieflex.service.MovieService;
import tech.suji.seven_prods.util.NotFoundException;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepository movieRepository;

	@Override
	public List<MovieDTO> findAll() {
		final List<Movie> movies = movieRepository.findAll(Sort.by("id"));
		return movies.stream().map(movie -> mapToDTO(movie, new MovieDTO())).toList();
	}

	@Override
	public MovieDTO get(final Long id) {
		return movieRepository.findById(id).map(movie -> mapToDTO(movie, new MovieDTO()))
				.orElseThrow(NotFoundException::new);
	}

	@Override
	public Long create(final MovieDTO movieDTO) {
		final Movie movie = new Movie();
		mapToEntity(movieDTO, movie);
		return movieRepository.save(movie).getId();
	}

	@Override
	public void update(final Long id, final MovieDTO movieDTO) {
		final Movie movie = movieRepository.findById(id).orElseThrow(NotFoundException::new);
		mapToEntity(movieDTO, movie);
		movieRepository.save(movie);
	}

	@Override
	public void delete(final Long id) {
		movieRepository.deleteById(id);
	}

	private MovieDTO mapToDTO(final Movie movie, final MovieDTO movieDTO) {
		movieDTO.setId(movie.getId());
		movieDTO.setTitle(movie.getTitle());
		movieDTO.setGenre(movie.getGenre());
		movieDTO.setDirector(movie.getDirector());
		movieDTO.setDuration(movie.getDuration());
		movieDTO.setRating(movie.getRating());
		movieDTO.setReleaseDate(movie.getReleaseDate());
		movieDTO.setCollection(movie.getCollection());
		return movieDTO;
	}

	private Movie mapToEntity(final MovieDTO movieDTO, final Movie movie) {
		movie.setTitle(movieDTO.getTitle());
		movie.setGenre(movieDTO.getGenre());
		movie.setDirector(movieDTO.getDirector());
		movie.setDuration(movieDTO.getDuration());
		movie.setRating(movieDTO.getRating());
		movie.setReleaseDate(movieDTO.getReleaseDate());
		movie.setCollection(movieDTO.getCollection());
		return movie;
	}

	@Override
	public boolean titleExists(final String title) {
		return movieRepository.existsByTitleIgnoreCase(title);
	}

	@Override
	public Object searchMovies(MovieSearchDTO searchDTO) {

		List<Specification<Movie>> specs = new ArrayList<>();

		if (StringUtils.isNotBlank(searchDTO.getTitle())) {
			Specification<Movie> nameLIke = (root, query, builder) -> builder.like(builder.lower(root.get("shiftName")),
					"%" + searchDTO.getTitle().toLowerCase() + "%");

			specs.add(nameLIke);
		}

		if (StringUtils.isNotBlank(searchDTO.getDirector())) {
			Specification<Movie> directorLIke = (root, query, builder) -> builder.like(builder.lower(root.get("shiftName")),
					"%" + searchDTO.getDirector().toLowerCase() + "%");

			specs.add(directorLIke);
		}

		if (searchDTO.getDuration() != null) {

		}

		if (searchDTO.getGenre() != null) {

		}

		if (searchDTO.getRating() != null) {

		}

		if (searchDTO.getPageNumber() != null) {

		}

		if (searchDTO.getPageSize() != null) {

		}

		if (searchDTO.getSortBy() != null) {

		}
		if (searchDTO.getOrderBy() != null) {

		}

		Specification<Movie> specifications = specs.stream().reduce(Specification.where(null), Specification::and);

		return null;
	}

	@Override
	public boolean existsByMovieName(String movieName) {
		return movieRepository.existsByTitleIgnoreCase(movieName);
	}

	@Override
	public List<Genre> getUniqueGenres() {
		return movieRepository.findDistinctGenres();
	}

}
