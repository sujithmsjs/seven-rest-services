package tech.suji.seven_prods.projects.quotes.model;
// QuoteSpecifications.java

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import tech.suji.seven_prods.projects.quotes.domain.Quote;
import tech.suji.seven_prods.projects.quotes.domain.Tag;
import tech.suji.seven_prods.projects.quotes.repos.QuoteRepository;

@Service
public class QuoteSpecifications {

	
	@Autowired
	private QuoteRepository quoteRepository;
	
    private Specification<Quote> buildSpecification(QuotePaginationDTO quotePaginationDTO) {
        return (root, query, criteriaBuilder) -> {
        	
            List<Predicate> predicates = new ArrayList<>();

            
            if (quotePaginationDTO.getSearchValue() != null && !quotePaginationDTO.getSearchValue().isEmpty()) {
                String searchValue = "%" + quotePaginationDTO.getSearchValue().toLowerCase() + "%";
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("quote")), searchValue),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), searchValue)
                ));
            }

            if (quotePaginationDTO.getAuthor() != null && !quotePaginationDTO.getAuthor().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), "%" + quotePaginationDTO.getAuthor().toLowerCase() + "%"));
            }

            List<Long> tags = quotePaginationDTO.getTags();
            if (tags != null && !tags.isEmpty()) {
                // Create a subquery to check if all specified tags are present for each quote
                Subquery<Long> tagSubquery = query.subquery(Long.class);
                Root<Tag> tagRoot = tagSubquery.from(Tag.class);
                tagSubquery.select(tagRoot.get("quote").get("id"));
                tagSubquery.where(tagRoot.get("id").in(tags));

                predicates.add(root.get("id").in(tagSubquery));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
    
    public Page<Quote> findBySearchValues(QuotePaginationDTO quotePaginationDTO) {
    	Specification<Quote> quoteSpecification = buildSpecification(quotePaginationDTO);
    	PageRequest pageRequest = PageRequest.of(quotePaginationDTO.getPageNumber(), quotePaginationDTO.getPageSize());
    	Page<Quote> page = quoteRepository.findAll(quoteSpecification, pageRequest);
    	return page;
    }
    
   
}
