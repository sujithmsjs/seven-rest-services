package tech.suji.seven_prods.projects.empmgnt.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.suji.seven_prods.projects.empmgnt.domain.Department;


public interface DepartmentRepository extends JpaRepository<Department, Long> {

    boolean existsByNameIgnoreCase(String name);

}
