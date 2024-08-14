package tech.suji.seven_prods.projects.quotes.rest;

import java.util.List;

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
import tech.suji.seven_prods.projects.quotes.model.TagCountDTO;
import tech.suji.seven_prods.projects.quotes.model.TagDTO;
import tech.suji.seven_prods.projects.quotes.repos.TagRepository.ITags;
import tech.suji.seven_prods.projects.quotes.service.TagService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/tags", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagResource {

    private final TagService tagService;

    public TagResource(final TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<TagDTO>> getAllTags() {
        return ResponseEntity.ok(tagService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> getTag(@PathVariable final Long id) {
        return ResponseEntity.ok(tagService.get(id));
    }
    
    @GetMapping("/counts")
    public ResponseEntity<List<TagCountDTO>> getTagCounts() {
        return ResponseEntity.ok(tagService.getTagCounts());
    }
    
    @GetMapping("/counts2")
    public ResponseEntity<List<ITags>> getTagCounts2() {
    	List<ITags> list = tagService.getTagCounts2();
    	
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<Long> createTag(@RequestBody @Valid final TagDTO tagDTO) {
        final Long createdId = tagService.create(tagDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateTag(@PathVariable final Long id,
            @RequestBody @Valid final TagDTO tagDTO) {
        tagService.update(id, tagDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable final Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
