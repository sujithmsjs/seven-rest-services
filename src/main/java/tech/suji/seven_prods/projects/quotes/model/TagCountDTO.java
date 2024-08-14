package tech.suji.seven_prods.projects.quotes.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TagCountDTO {

	private Long id;
	private String name;
	private Long count;
	
	public TagCountDTO(Long id, String name, Long count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }
}
