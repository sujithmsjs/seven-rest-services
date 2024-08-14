package tech.suji.seven_prods.projects.movieflex.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import tech.suji.seven_prods.projects.movieflex.dto.Genre;
import tech.suji.seven_prods.projects.movieflex.dto.MovieDTO;
import tech.suji.seven_prods.projects.movieflex.service.MovieService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieController {

	private final MovieService movieService;

	public MovieController(final MovieService movieService) {
		this.movieService = movieService;
	}

	@GetMapping
	public ResponseEntity<List<MovieDTO>> getAllMovies() {
		return ResponseEntity.ok(movieService.findAll());
	}

	@GetMapping("/exists/{movieName}")
	public boolean existsByMovie(@PathVariable String movieName) {
		return movieService.existsByMovieName(movieName);
	}

	@GetMapping("/genre")
	public List<Genre> getUniqueGenres() {
		return movieService.getUniqueGenres();
	}

	@GetMapping("/late")
	public ResponseEntity<Map<String, String>> getLateReply() throws InterruptedException {
		Thread.sleep(3_000);
		Map<String, String> map = new HashMap<>();
		;

		Random r = new Random();
		if(r.nextBoolean()) {
			map.put("result", "Success");
			return ResponseEntity.ok(map);
		}else {
			map.put("result", "Failed");
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.BAD_REQUEST);
		}


	}

	@GetMapping("/{id}")
	public ResponseEntity<MovieDTO> getMovie(@PathVariable(name = "id") final Long id) {
		return ResponseEntity.ok(movieService.get(id));
	}

	@PostMapping
	@ApiResponse(responseCode = "201")
	public ResponseEntity<Long> createMovie(@RequestBody @Valid final MovieDTO movieDTO) {
		final Long createdId = movieService.create(movieDTO);
		return new ResponseEntity<>(createdId, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Long> updateMovie(@PathVariable(name = "id") final Long id,
			@RequestBody @Valid final MovieDTO movieDTO) {
		movieService.update(id, movieDTO);
		return ResponseEntity.ok(id);
	}

	@DeleteMapping("/{id}")
	@ApiResponse(responseCode = "204")
	public ResponseEntity<Void> deleteMovie(@PathVariable(name = "id") final Long id) {
		movieService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
