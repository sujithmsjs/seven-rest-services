package tech.suji.seven_prods.projects.quotes.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tech.suji.seven_prods.exception.CustomException;
import tech.suji.seven_prods.projects.quotes.domain.Quote;
import tech.suji.seven_prods.projects.quotes.model.QuoteDTO;
import tech.suji.seven_prods.projects.quotes.model.QuoteJsonDTO;
import tech.suji.seven_prods.projects.quotes.model.QuotePaginationDTO;
import tech.suji.seven_prods.projects.quotes.model.QuoteSpecifications;
import tech.suji.seven_prods.projects.quotes.service.QuoteService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/quotes", produces = MediaType.APPLICATION_JSON_VALUE)
//@Validated
public class QuoteResource {

	@Autowired
	private QuoteService quoteService;

	@Autowired
	private QuoteSpecifications quoteSpecifications;

	@GetMapping
	public ResponseEntity<List<Quote>> getAllQuotes() {
		return ResponseEntity.ok(quoteService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<QuoteDTO> getQuote(@PathVariable final Long id) {
		return ResponseEntity.ok(quoteService.get(id));
	}

	@PostMapping("/json-load")
	public Map<String, Object> loadJSON(@Valid @RequestBody final List<QuoteJsonDTO> quoteDTO) {

		return quoteService.loadJson(quoteDTO);

	}
	
	@PostMapping("/remove-dupes")
	public List<QuoteJsonDTO> removeDupes(@Valid @RequestBody final List<QuoteJsonDTO> quoteDTO) {

		return quoteService.removeDups(quoteDTO);

	}

	@PostMapping
	public ResponseEntity<Long> createQuote(@RequestBody @Valid final QuoteDTO quoteDTO) {
		Long createdId = 0L;
		try {
			createdId = quoteService.create(quoteDTO);
		} catch (Exception ex) {
			throw new CustomException(ex.getMessage());
		}
		return new ResponseEntity<>(createdId, HttpStatus.CREATED);
	}

	@PostMapping("/search")
	public ResponseEntity<Page<Quote>> createQuote(@RequestBody @Valid final QuotePaginationDTO quoteDTO) {
		Page<Quote> page = quoteSpecifications.findBySearchValues(quoteDTO);
		return new ResponseEntity<>(page, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Long> updateQuote(@PathVariable final Long id, @RequestBody @Valid final QuoteDTO quoteDTO) {
		quoteService.update(id, quoteDTO);
		return ResponseEntity.ok(id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteQuote(@PathVariable final Long id) {
		quoteService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
