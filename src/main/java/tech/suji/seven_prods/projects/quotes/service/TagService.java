package tech.suji.seven_prods.projects.quotes.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tech.suji.seven_prods.projects.quotes.domain.Tag;
import tech.suji.seven_prods.projects.quotes.model.TagCountDTO;
import tech.suji.seven_prods.projects.quotes.model.TagDTO;
import tech.suji.seven_prods.projects.quotes.repos.QuoteRepository;
import tech.suji.seven_prods.projects.quotes.repos.TagRepository;
import tech.suji.seven_prods.projects.quotes.repos.TagRepository.ITags;
import tech.suji.seven_prods.util.NotFoundException;


@Service
@Transactional
public class TagService {

    private final TagRepository tagRepository;
    private final QuoteRepository quoteRepository;

    public TagService(final TagRepository tagRepository, final QuoteRepository quoteRepository) {
        this.tagRepository = tagRepository;
        this.quoteRepository = quoteRepository;
    }

    public List<TagDTO> findAll() {
        final List<Tag> tags = tagRepository.findAll(Sort.by("id"));
        return tags.stream()
                .map(tag -> mapToDTO(tag, new TagDTO()))
                .toList();
    }

    public TagDTO get(final Long id) {
        return tagRepository.findById(id)
                .map(tag -> mapToDTO(tag, new TagDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TagDTO tagDTO) {
        final Tag tag = new Tag();
        mapToEntity(tagDTO, tag);
        return tagRepository.save(tag).getId();
    }

    public void update(final Long id, final TagDTO tagDTO) {
        final Tag tag = tagRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(tagDTO, tag);
        tagRepository.save(tag);
    }

    public void delete(final Long id) {
        final Tag tag = tagRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        quoteRepository.findAllByTags(tag)
                .forEach(quote -> quote.getTags().remove(tag));
        tagRepository.delete(tag);
    }

    private TagDTO mapToDTO(final Tag tag, final TagDTO tagDTO) {
        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());
        return tagDTO;
    }

    private Tag mapToEntity(final TagDTO tagDTO, final Tag tag) {
        tag.setName(tagDTO.getName());
        return tag;
    }

    public boolean nameExists(final String name) {
        return tagRepository.existsByNameIgnoreCase(name);
    }

	public List<TagCountDTO> getTagCounts() {
		List<TagCountDTO> tagCounts = tagRepository.getTagCountsJQPL();
		return tagCounts;
	}
	
	public List<ITags> getTagCounts2() {
		List<ITags> tagCount = tagRepository.getTagCountWithNQ();
		return tagCount;
	}
	
	

}
