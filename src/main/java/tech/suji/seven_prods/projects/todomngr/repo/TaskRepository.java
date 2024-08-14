package tech.suji.seven_prods.projects.todomngr.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.suji.seven_prods.projects.empmgnt.domain.Task;


public interface TaskRepository extends JpaRepository<Task, Long> {
}
