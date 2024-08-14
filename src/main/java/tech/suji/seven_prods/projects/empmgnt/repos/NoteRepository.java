package tech.suji.seven_prods.projects.empmgnt.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.suji.seven_prods.projects.empmgnt.domain.Note;


public interface NoteRepository extends JpaRepository<Note, Long> {
}
