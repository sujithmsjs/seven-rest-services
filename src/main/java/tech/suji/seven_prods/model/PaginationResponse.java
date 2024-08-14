package tech.suji.seven_prods.model;

import java.util.List;

import lombok.Data;

@Data
public class PaginationResponse<T> {

	private int number;
	private int size;
	private int totalPages;
	private long totalElements;
	private List<T> content;
}
