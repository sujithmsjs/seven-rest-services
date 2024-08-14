package tech.suji.seven_prods.projects.empmgnt.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tech.suji.seven_prods.projects.empmgnt.domain.Department;
import tech.suji.seven_prods.projects.empmgnt.model.DepartmentDTO;
import tech.suji.seven_prods.projects.empmgnt.repos.DepartmentRepository;
import tech.suji.seven_prods.util.NotFoundException;


@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(final DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<DepartmentDTO> findAll() {
        final List<Department> departments = departmentRepository.findAll(Sort.by("id").descending());
        return departments.stream()
                .map(department -> mapToDTO(department, new DepartmentDTO()))
                .toList();
    }

    public DepartmentDTO get(final Long id) {
        return departmentRepository.findById(id)
                .map(department -> mapToDTO(department, new DepartmentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DepartmentDTO departmentDTO) {
        final Department department = new Department();
        mapToEntity(departmentDTO, department);
        return departmentRepository.save(department).getId();
    }

    public void update(final Long id, final DepartmentDTO departmentDTO) {
        final Department department = departmentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(departmentDTO, department);
        departmentRepository.save(department);
    }

    public void delete(final Long id) {
        departmentRepository.deleteById(id);
    }

    private DepartmentDTO mapToDTO(final Department department, final DepartmentDTO departmentDTO) {
  
    	departmentDTO.setId(department.getId());
        departmentDTO.setName(department.getName());
        departmentDTO.setLocation(department.getLocation());
        departmentDTO.setActive(department.isActive());
        departmentDTO.setLastUpdated(department.getLastUpdated().toLocalDateTime());
        
        return departmentDTO;
    }

    private Department mapToEntity(final DepartmentDTO departmentDTO, final Department department) {
        department.setName(departmentDTO.getName());
        department.setLocation(departmentDTO.getLocation());
        department.setActive(departmentDTO.isActive());
        return department;
    }

    public boolean nameExists(final String name) {
        return departmentRepository.existsByNameIgnoreCase(name);
    }

}
