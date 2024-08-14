package tech.suji.seven_prods.projects.movieflex.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDTO {

	private Long id;

	@NotNull
	@Size(max = 255)
	private String title;

	@NotNull
	private Genre genre;

	@Size(max = 255)
	private String director;

	private Integer duration;

	private Double rating;

	private LocalDate releaseDate;

	@Digits(integer = 10, fraction = 2)
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@Schema(type = "string", example = "50.08")
	private BigDecimal collection;

}
