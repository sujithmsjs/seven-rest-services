package tech.suji.seven_prods.projects.quotes.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import tech.suji.seven_prods.exception.CustomException;
import tech.suji.seven_prods.projects.quotes.domain.Category;
import tech.suji.seven_prods.projects.quotes.domain.Quote;
import tech.suji.seven_prods.projects.quotes.domain.Tag;
import tech.suji.seven_prods.projects.quotes.model.QuoteDTO;
import tech.suji.seven_prods.projects.quotes.model.QuoteJsonDTO;
import tech.suji.seven_prods.projects.quotes.repos.CategoryRepository;
import tech.suji.seven_prods.projects.quotes.repos.QuoteRepository;
import tech.suji.seven_prods.projects.quotes.repos.TagRepository;
import tech.suji.seven_prods.projects.quotes.repos.TagRepository.ITags;
import tech.suji.seven_prods.util.NotFoundException;

@Service
public class QuoteService {

	private final QuoteRepository quoteRepository;
	private final CategoryRepository categoryRepository;
	private final TagRepository tagRepository;

	public QuoteService(final QuoteRepository quoteRepository, final CategoryRepository categoryRepository,
			final TagRepository tagRepository) {
		this.quoteRepository = quoteRepository;
		this.categoryRepository = categoryRepository;
		this.tagRepository = tagRepository;
	}

	public List<Quote> findAll() {
		final List<Quote> quotes = quoteRepository.findAll(Sort.by("id").descending());
		return quotes;
		// return quotes.stream().map(quote -> mapToDTO(quote, new
		// QuoteDTO())).toList();
	}

	public QuoteDTO get(final Long id) {
		return quoteRepository.findById(id).map(quote -> mapToDTO(quote, new QuoteDTO()))
				.orElseThrow(NotFoundException::new);
	}

	@Transactional
	public Long create(final QuoteDTO quoteDTO) {
		final Quote quote = new Quote();
		mapToEntity(quoteDTO, quote);
		Long id = 0L;
		try {
			id = quoteRepository.save(quote).getId();
		} catch (Exception ex) {
			throw new CustomException(ex.getMessage());
		}
		return id;
	}

	public void update(final Long id, final QuoteDTO quoteDTO) {
		final Quote quote = quoteRepository.findById(id).orElseThrow(NotFoundException::new);
		mapToEntity(quoteDTO, quote);
		quoteRepository.save(quote);
	}

	public void delete(final Long id) {
		quoteRepository.deleteById(id);
	}

	private QuoteDTO mapToDTO(final Quote quote, final QuoteDTO quoteDTO) {
		// quoteDTO.setId(quote.getId());
		quoteDTO.setQuote(quote.getQuote());
		quoteDTO.setAuthor(quote.getAuthor());
		quoteDTO.setReference(quote.getReference());

		if (quote.getCategory() != null) {
			quoteDTO.setCatId(quote.getCategory().getId() == null ? null : quote.getCategory().getId());
		}

		quoteDTO.setTags(quote.getTags().stream().map(tag -> tag.getId()).collect(Collectors.toSet()));
		return quoteDTO;
	}

	private Quote mapToEntity(final QuoteDTO quoteDTO, final Quote quote) {
		quote.setQuote(quoteDTO.getQuote());
		quote.setAuthor(quoteDTO.getAuthor());
		quote.setReference(quoteDTO.getReference());
		final Category cid = quoteDTO.getCatId() == null ? null
				: categoryRepository.findById(quoteDTO.getCatId())
						.orElseThrow(() -> new NotFoundException("Category Id not found"));
		quote.setCategory(cid);

		final List<Tag> tags = tagRepository.findAllById(quoteDTO.getTags());

		if (tags.size() != (quoteDTO.getTags() == null ? 0 : quoteDTO.getTags().size())) {
			throw new NotFoundException("one of Tag Id not found");
		}
		quote.setTags(tags.stream().collect(Collectors.toSet()));
		return quote;
	}

	public Map<String, Object> loadJson(@Valid List<QuoteJsonDTO> quoteDTO) {
		var map = new HashMap<String, Object>();
		List<ITags> tagsList = tagRepository.getTagCountWithNQ();
		List<String> allTags = tagsList.stream().map(tag -> tag.getName()).collect(Collectors.toList());
		int count = 0;
		int pass = 0;
		int fail = 0;
		List<QuoteJsonDTO> failedQuotes = new ArrayList<>();
		for (QuoteJsonDTO quote : quoteDTO) {
			count++;
			try {
				saveQuote(allTags, quote);
				pass++;
			} catch (Exception ex) {
				fail++;
				failedQuotes.add(quote);
			}
		}
		map.put("total", count);
		map.put("fail", fail);
		map.put("pass", pass);
		map.put("failedQuotes", failedQuotes);

		return map;
	}

	private void saveQuote(List<String> allTags, QuoteJsonDTO quote) {
		Set<String> oldQuteTags = quote.getTags();

		// Clone the tags
		List<String> tagsClong = oldQuteTags.stream().collect(Collectors.toList());

		boolean allRemoved = oldQuteTags.removeAll(allTags);

		if (!oldQuteTags.isEmpty()) {
			oldQuteTags.stream().forEach(t -> {
				Tag newTag = new Tag();
				newTag.setName(t);
				Tag save = tagRepository.save(newTag);
			});
		}
		Quote newQuote = new Quote();
		newQuote.setQuote(quote.getQuote());
		newQuote.setAuthor(quote.getAuthor());
		newQuote.setReference(quote.getReference());
		// Set<String> quoteTags = quote.getTags();

		List<ITags> refreshedTagList = tagRepository.getTagCountWithNQ();
		Set<Tag> newQuoteTags = refreshedTagList.stream().filter(t -> tagsClong.contains(t.getName())).map(t -> {
			Tag tag = new Tag();
			tag.setId(t.getId());
			tag.setName(t.getName());
			return tag;
		}).collect(Collectors.toSet());

		newQuote.setTags(newQuoteTags);
		quoteRepository.save(newQuote);
	}

	public List<QuoteJsonDTO> removeDups(@Valid List<QuoteJsonDTO> quoteDTO) {

		List<QuoteJsonDTO> list = quoteDTO.stream().filter(q -> !quoteRepository.existsByQuote(q.getQuote()))
				.collect(Collectors.toList());

		return list;
	}
}
