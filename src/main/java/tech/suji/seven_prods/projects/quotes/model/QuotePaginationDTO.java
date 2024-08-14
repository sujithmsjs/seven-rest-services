package tech.suji.seven_prods.projects.quotes.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuotePaginationDTO {
	int pageNumber;
	int pageSize;
	private String searchValue;
	private String author;
	private List<Long> tags;
}
