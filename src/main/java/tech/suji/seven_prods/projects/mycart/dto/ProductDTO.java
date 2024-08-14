package tech.suji.seven_prods.projects.mycart.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductDTO {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String name;

    @Size(max = 255)
    private String description;

    @NotNull
    private Integer price;
    
    private LocalDate releaseDate;

    private Integer rating;

    @Size(max = 20)
    private String category;

    private Integer stock;

}
