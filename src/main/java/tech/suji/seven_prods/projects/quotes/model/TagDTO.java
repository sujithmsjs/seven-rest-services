package tech.suji.seven_prods.projects.quotes.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TagDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

}
