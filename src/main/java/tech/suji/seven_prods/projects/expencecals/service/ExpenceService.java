package tech.suji.seven_prods.projects.expencecals.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tech.suji.seven_prods.projects.expencecals.dto.ExpenceDTO;
import tech.suji.seven_prods.projects.expencecals.entity.Expence;
import tech.suji.seven_prods.projects.expencecals.repo.ExpenceRepository;
import tech.suji.seven_prods.util.NotFoundException;


@Service
public class ExpenceService {

    private final ExpenceRepository expenceRepository;

    public ExpenceService(final ExpenceRepository expenceRepository) {
        this.expenceRepository = expenceRepository;
    }

    public List<ExpenceDTO> findAll() {
        final List<Expence> expences = expenceRepository.findAll(Sort.by("id"));
        return expences.stream()
                .map(expence -> mapToDTO(expence, new ExpenceDTO()))
                .toList();
    }

    public ExpenceDTO get(final Long id) {
        return expenceRepository.findById(id)
                .map(expence -> mapToDTO(expence, new ExpenceDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ExpenceDTO expenceDTO) {
        final Expence expence = new Expence();
        mapToEntity(expenceDTO, expence);
        return expenceRepository.save(expence).getId();
    }

    public void update(final Long id, final ExpenceDTO expenceDTO) {
        final Expence expence = expenceRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(expenceDTO, expence);
        expenceRepository.save(expence);
    }

    public void delete(final Long id) {
        expenceRepository.deleteById(id);
    }

    private ExpenceDTO mapToDTO(final Expence expence, final ExpenceDTO expenceDTO) {
        expenceDTO.setId(expence.getId());
        expenceDTO.setReason(expence.getReason());
        expenceDTO.setAmount(expence.getAmount());
        expenceDTO.setDate(expence.getDate());
        expenceDTO.setCategory(expence.getCategory());
        return expenceDTO;
    }

    private Expence mapToEntity(final ExpenceDTO expenceDTO, final Expence expence) {
        expence.setReason(expenceDTO.getReason());
        expence.setAmount(expenceDTO.getAmount());
        expence.setDate(expenceDTO.getDate());
        expence.setCategory(expenceDTO.getCategory());
        return expence;
    }

}
