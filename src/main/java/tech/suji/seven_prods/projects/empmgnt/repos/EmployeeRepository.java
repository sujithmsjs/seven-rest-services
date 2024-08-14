package tech.suji.seven_prods.projects.empmgnt.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.suji.seven_prods.projects.empmgnt.domain.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByNameIgnoreCase(String firstName);

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmailIgnoreCase(String email);

}
