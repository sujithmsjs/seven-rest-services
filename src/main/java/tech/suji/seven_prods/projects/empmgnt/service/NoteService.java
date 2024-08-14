package tech.suji.seven_prods.projects.empmgnt.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tech.suji.seven_prods.projects.empmgnt.domain.Employee;
import tech.suji.seven_prods.projects.empmgnt.domain.Note;
import tech.suji.seven_prods.projects.empmgnt.model.NoteDTO;
import tech.suji.seven_prods.projects.empmgnt.repos.EmployeeRepository;
import tech.suji.seven_prods.projects.empmgnt.repos.NoteRepository;
import tech.suji.seven_prods.util.NotFoundException;


@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final EmployeeRepository employeeRepository;

    public NoteService(final NoteRepository noteRepository,
            final EmployeeRepository employeeRepository) {
        this.noteRepository = noteRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<NoteDTO> findAll() {
        final List<Note> notes = noteRepository.findAll(Sort.by("id"));
        return notes.stream()
                .map(note -> mapToDTO(note, new NoteDTO()))
                .toList();
    }

    public NoteDTO get(final Long id) {
        return noteRepository.findById(id)
                .map(note -> mapToDTO(note, new NoteDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final NoteDTO noteDTO) {
        final Note note = new Note();
        mapToEntity(noteDTO, note);
        return noteRepository.save(note).getId();
    }

    public void update(final Long id, final NoteDTO noteDTO) {
        final Note note = noteRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(noteDTO, note);
        noteRepository.save(note);
    }

    public void delete(final Long id) {
        noteRepository.deleteById(id);
    }

    private NoteDTO mapToDTO(final Note note, final NoteDTO noteDTO) {
        noteDTO.setId(note.getId());
        noteDTO.setTitle(note.getTitle());
        noteDTO.setContent(note.getContent());
        noteDTO.setEmployee(note.getEmployee() == null ? null : note.getEmployee().getId());
        return noteDTO;
    }

    private Note mapToEntity(final NoteDTO noteDTO, final Note note) {
        note.setTitle(noteDTO.getTitle());
        note.setContent(noteDTO.getContent());
        final Employee employee = noteDTO.getEmployee() == null ? null : employeeRepository.findById(noteDTO.getEmployee())
                .orElseThrow(() -> new NotFoundException("employee not found"));
        note.setEmployee(employee);
        return note;
    }

}
