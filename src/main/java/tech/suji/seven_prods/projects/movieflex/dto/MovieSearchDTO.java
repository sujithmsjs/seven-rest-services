package tech.suji.seven_prods.projects.movieflex.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieSearchDTO {
    private String title;
    private Genre genre;
    private String director;
    private Integer duration;
    private Double rating;
    private BigDecimal collection;
    private LocalDate releaseDate;
    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private String orderBy;
}
