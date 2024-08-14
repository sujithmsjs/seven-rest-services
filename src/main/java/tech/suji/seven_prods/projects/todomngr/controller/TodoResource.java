package tech.suji.seven_prods.projects.todomngr.controller;

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

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import tech.suji.seven_prods.projects.todomngr.dto.TodoDTO;
import tech.suji.seven_prods.projects.todomngr.dto.TodoResponse;
import tech.suji.seven_prods.projects.todomngr.entity.Todo;
import tech.suji.seven_prods.projects.todomngr.service.TodoService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/todos", produces = MediaType.APPLICATION_JSON_VALUE)
public class TodoResource {

    private final TodoService todoService;

    public TodoResource(final TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getAllTodos() {
        return ResponseEntity.ok(todoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDTO> getTodo(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(todoService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createTodo(@RequestBody @Valid final TodoDTO todoDTO) {
        final Long createdId = todoService.create(todoDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateTodo(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final TodoDTO todoDTO) {
        todoService.update(id, todoDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTodo(@PathVariable(name = "id") final Long id) {
        todoService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    
    @GetMapping("/start/{id}")
    public ResponseEntity<String> getStartTodo(@PathVariable(name = "id") final Long id) {
    	todoService.setTodoStartTime(id);
        return ResponseEntity.ok("Timer get started");
    }
    
    @GetMapping("/stop/{id}")
    public ResponseEntity<String> getStopTodo(@PathVariable(name = "id") final Long id) {
    	todoService.setTodoStopTime(id);
        return ResponseEntity.ok("Timer stopped");
    }
    
    @GetMapping("/timer/{id}")
    public ResponseEntity<Todo> getTimerDetails(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(todoService.getTimerDetails(id));
    }
    
    @GetMapping("/finish/{id}")
    public ResponseEntity<Todo> finishTodo(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(todoService.setFinishTodo(id));
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
