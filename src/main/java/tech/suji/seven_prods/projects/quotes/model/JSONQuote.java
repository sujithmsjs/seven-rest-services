package tech.suji.seven_prods.projects.quotes.model;

import java.util.Set;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class JSONQuote {
	@NotNull
	@Size(max = 255)
	private String quote;

	private String desc;

	@Size(max = 50)
	private String author;

	@Size(max = 50)
	private String ref;

	private Set<String> tags;
}
