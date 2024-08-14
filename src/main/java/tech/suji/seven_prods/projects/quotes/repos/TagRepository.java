package tech.suji.seven_prods.projects.quotes.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tech.suji.seven_prods.projects.quotes.domain.Tag;
import tech.suji.seven_prods.projects.quotes.model.TagCountDTO;

public interface TagRepository extends JpaRepository<Tag, Long> {

	boolean existsByNameIgnoreCase(String name);

	@Query("SELECT new tech.suji.seven_prods.projects.quotes.model.TagCountDTO(t.id,t.name,count(q.id)) FROM Quote q JOIN q.tags t GROUP BY t.id")
	List<TagCountDTO> getTagCountsJQPL();

	@Query(value = "SELECT t.id,t.name, count(tq.quote_id) count FROM tags t  left join quote_tags tq on t.id = tq.tag_id group by t.id",
			nativeQuery = true)
	List<ITags> getTagCountWithNQ();

	
	public static interface ITags {
		Long getId();

		String getName();

		Integer getCount();
	}

	// SELECT q.id, q.quote, t.id, t.name FROM Quote q JOIN q.tags t
	// SELECT t.id,t.name,count(q.id) FROM Quote q JOIN q.tags t GROUP BY t.id
	// SELECT new
	// tech.suji.seven_prods.projects.quotes.model.TagCountDTO(t.id,t.name,count(q.id))
	// FROM Quote q Right JOIN q.tags t GROUP BY t.id
}
