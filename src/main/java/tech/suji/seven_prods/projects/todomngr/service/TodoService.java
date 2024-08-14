package tech.suji.seven_prods.projects.todomngr.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tech.suji.seven_prods.exception.CustomException;
import tech.suji.seven_prods.projects.todomngr.dto.TodoDTO;
import tech.suji.seven_prods.projects.todomngr.dto.TodoResponse;
import tech.suji.seven_prods.projects.todomngr.dto.TodoStatusType;
import tech.suji.seven_prods.projects.todomngr.entity.Todo;
import tech.suji.seven_prods.projects.todomngr.repo.TodoRepository;
import tech.suji.seven_prods.util.NotFoundException;
import tech.suji.seven_prods.util.Utility;

@Service
public class TodoService {

	@Autowired
	private Utility utility;

	private final TodoRepository todoRepository;

	public TodoService(final TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

//	public List<TodoDTO> findAll() {
//		final List<Todo> todos = todoRepository.findAll(Sort.by("id"));
//		return todos.stream().map(todo -> mapToDTO(todo, new TodoDTO())).toList();
//	}

	public List<TodoResponse> findAll() {
		final List<Todo> todos = todoRepository.findAll(Sort.by("id"));

		List<TodoResponse> resTodos = todos.stream().map(todo -> {
			TodoResponse r = new TodoResponse();
			r.setId(todo.getId());
			r.setTitle(todo.getTitle());
			r.setDescription(todo.getDescription());
			r.setStatus(todo.getStatus());
			r.setPriority(todo.getPriority());
			r.setTimeReq(todo.getTimeReq().toSeconds());
			setTimeOut(todo);
			// if(todo.getStatus() == TodoStatusType.IN_PROGRESS) {
			r.setTimeLeft(todo.getTimeLeft().toSeconds());
			// }

			return r;
		}).collect(Collectors.toList());

		return resTodos;
	}

	public TodoDTO get(final Long id) {
		return todoRepository.findById(id).map(todo -> mapToDTO(todo, new TodoDTO()))
				.orElseThrow(NotFoundException::new);
	}

	public Long create(final TodoDTO todoDTO) {
		final Todo todo = new Todo();
		Duration duration = utility.getDuration(todoDTO.getTimeRequired());
		todo.setTitle(todoDTO.getTitle());
		todo.setTimeReq(duration);
		todo.setTimeLeft(duration);
		todo.setDescription(todoDTO.getDescription());
		todo.setPriority(todoDTO.getPriority());
		todo.setStatus(TodoStatusType.NOT_STARTED);
		return todoRepository.save(todo).getId();
	}

	public void update(final Long id, final TodoDTO todoDTO) {
		final Todo todo = todoRepository.findById(id).orElseThrow(NotFoundException::new);
		Duration duration = utility.getDuration(todoDTO.getTimeRequired());
		todo.setTimeReq(duration);
		todo.setTitle(todoDTO.getTitle());
		todo.setTimeLeft(duration);
		todo.setDescription(todoDTO.getDescription());
		todo.setPriority(todoDTO.getPriority());
		todo.setStatus(TodoStatusType.NOT_STARTED);
		todoRepository.save(todo);
	}

	public void delete(final Long id) {
		todoRepository.deleteById(id);
	}

	private TodoDTO mapToDTO(final Todo todo, final TodoDTO todoDTO) {
		todoDTO.setId(todo.getId());
		todoDTO.setTitle(todo.getTitle());
		todoDTO.setDescription(todo.getDescription());
		// todoDTO.setDueDate(todo.getDueDate());
		todoDTO.setPriority(todo.getPriority());
		// todoDTO.setStatus(todo.getStatus());
		todoDTO.setIsDone(todo.getIsDone());
		return todoDTO;
	}

	private Todo mapToEntity(final TodoDTO todoDTO, final Todo todo) {
		todo.setTitle(todoDTO.getTitle());
		todo.setDescription(todoDTO.getDescription());
		// todo.setDueDate(todoDTO.getDueDate());
		todo.setPriority(todoDTO.getPriority());
		// todo.setStatus(todoDTO.getStatus());
		todo.setIsDone(todoDTO.getIsDone());
		return todo;
	}

	public Todo setTodoStartTime(Long id) {
		Todo todo = todoRepository.findById(id).orElseThrow(() -> new CustomException("TODO not found with id " + id));
		if (todo.getStatus() == TodoStatusType.TIME_OUT) {
			throw new CustomException("Sorry, this TODO already timeout.");
		}
		todo.setStatus(TodoStatusType.IN_PROGRESS);
		todo.setTimeStartAt(LocalDateTime.now());

		return todoRepository.save(todo);
	}

	public Todo setTodoStopTime(Long id) {
		Todo todo = getTodoAfterSeTimeLeft(id);
		todo.setTimeStartAt(null);
		todo.setStatus(TodoStatusType.PAUSED);
		return todoRepository.save(todo);
	}

	private Todo getTodoAfterSeTimeLeft(Long id) {
		Todo todo = todoRepository.findById(id).orElseThrow(() -> new CustomException("TODO not found with id " + id));
		setTimeOut(todo);
		return todo;
	}

	private void setTimeOut(Todo todo) {
		// Calculating duration
		if (todo.getStatus() != TodoStatusType.IN_PROGRESS) {
			return;
		}
		LocalDateTime fromTime = todo.getTimeStartAt();
		LocalDateTime toTime = LocalDateTime.now();
		Duration duration = Duration.between(fromTime, toTime);
		Duration timeLeft = todo.getTimeLeft().minus(duration);
		if (timeLeft.isNegative()) {
			todo.setStatus(TodoStatusType.TIME_OUT);
		}
		todo.setTimeLeft(timeLeft);
	}

	public Todo getTimerDetails(Long id) {
		Todo todo = getTodoAfterSeTimeLeft(id);
		return todo;
	}

	public Todo setFinishTodo(Long id) {
		Todo todo = todoRepository.findById(id).orElseThrow(() -> new CustomException("TODO not found with id " + id));
		todo.setStatus(TodoStatusType.SUCCESS);
		setTimeOut(todo);
		return todoRepository.save(todo);
	}

}
