package tech.suji.seven_prods.projects.empmgnt.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.suji.seven_prods.projects.empmgnt.domain.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByNameIgnoreCase(String name);

}
