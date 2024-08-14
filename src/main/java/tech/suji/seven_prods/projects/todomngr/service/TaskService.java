package tech.suji.seven_prods.projects.todomngr.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tech.suji.seven_prods.projects.empmgnt.domain.Employee;
import tech.suji.seven_prods.projects.empmgnt.domain.Task;
import tech.suji.seven_prods.projects.empmgnt.repos.EmployeeRepository;
import tech.suji.seven_prods.projects.todomngr.dto.TaskDTO;
import tech.suji.seven_prods.projects.todomngr.repo.TaskRepository;
import tech.suji.seven_prods.util.NotFoundException;


@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    public TaskService(final TaskRepository taskRepository,
            final EmployeeRepository employeeRepository) {
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<TaskDTO> findAll() {
        final List<Task> tasks = taskRepository.findAll(Sort.by("id"));
        return tasks.stream()
                .map(task -> mapToDTO(task, new TaskDTO()))
                .toList();
    }

    public TaskDTO get(final Long id) {
        return taskRepository.findById(id)
                .map(task -> mapToDTO(task, new TaskDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TaskDTO taskDTO) {
        final Task task = new Task();
        mapToEntity(taskDTO, task);
        return taskRepository.save(task).getId();
    }

    public void update(final Long id, final TaskDTO taskDTO) {
        final Task task = taskRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(taskDTO, task);
        taskRepository.save(task);
    }

    public void delete(final Long id) {
        taskRepository.deleteById(id);
    }

    private TaskDTO mapToDTO(final Task task, final TaskDTO taskDTO) {
        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setIsDone(task.getIsDone());
        taskDTO.setTargetDate(task.getTargetDate());
        taskDTO.setEmployee(task.getEmployee() == null ? null : task.getEmployee().getId());
        return taskDTO;
    }

    private Task mapToEntity(final TaskDTO taskDTO, final Task task) {
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setIsDone(taskDTO.getIsDone());
        task.setTargetDate(taskDTO.getTargetDate());
        final Employee employee = taskDTO.getEmployee() == null ? null : employeeRepository.findById(taskDTO.getEmployee())
                .orElseThrow(() -> new NotFoundException("employee not found"));
        task.setEmployee(employee);
        return task;
    }

}
