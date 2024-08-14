package tech.suji.seven_prods.projects.quotes.model;

import java.util.Set;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class QuoteJsonDTO {
	@NotNull
	@Size(min = 4, max = 50)
	private String quote;

	@Size(min = 4, max = 20)
	private String author;

	@Size(max = 50)
	private String reference;

	@Size(min = 1)
	private Set<String> tags;
}
