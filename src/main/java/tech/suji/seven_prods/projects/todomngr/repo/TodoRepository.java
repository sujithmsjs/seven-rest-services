package tech.suji.seven_prods.projects.todomngr.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.suji.seven_prods.projects.todomngr.entity.Todo;


public interface TodoRepository extends JpaRepository<Todo, Long> {
}
