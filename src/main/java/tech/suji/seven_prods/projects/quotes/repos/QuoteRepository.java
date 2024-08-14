package tech.suji.seven_prods.projects.quotes.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import tech.suji.seven_prods.projects.quotes.domain.Quote;
import tech.suji.seven_prods.projects.quotes.domain.Tag;


public interface QuoteRepository extends JpaRepository<Quote, Long>, JpaSpecificationExecutor<Quote> {

    List<Quote> findAllByTags(Tag tag);
    
    boolean existsByQuote(String quote);

}
