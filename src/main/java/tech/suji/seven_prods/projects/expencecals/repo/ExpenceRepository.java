package tech.suji.seven_prods.projects.expencecals.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.suji.seven_prods.projects.expencecals.entity.Expence;


public interface ExpenceRepository extends JpaRepository<Expence, Long> {
}
