package tech.suji.seven_prods.projects.expencecals.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.suji.seven_prods.projects.expencecals.dto.ExpenceDTO;
import tech.suji.seven_prods.projects.expencecals.service.ExpenceService;


@RestController
@RequestMapping(value = "/api/expences", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExpenceResource {

    private final ExpenceService expenceService;

    public ExpenceResource(final ExpenceService expenceService) {
        this.expenceService = expenceService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenceDTO>> getAllExpences() {
        return ResponseEntity.ok(expenceService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenceDTO> getExpence(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(expenceService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createExpence(@RequestBody @Valid final ExpenceDTO expenceDTO) {
        final Long createdId = expenceService.create(expenceDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateExpence(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ExpenceDTO expenceDTO) {
        expenceService.update(id, expenceDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteExpence(@PathVariable(name = "id") final Long id) {
        expenceService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
