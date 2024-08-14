package tech.suji.seven_prods.projects.quotes.model;

import java.util.Set;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import tech.suji.seven_prods.projects.quotes.domain.Tag;


@Getter
@Setter
public class QuoteDTO {

    @NotNull
    @Size(max = 255)
    private String quote;

    @Size(max = 50)
    private String author;

    @Size(max = 50)
    private String reference;

    private Long catId;

    private Set<Long> tags;

}
