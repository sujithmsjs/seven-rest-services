package tech.suji.seven_prods.projects.cats.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.suji.seven_prods.projects.cats.entity.Cat;

public interface CatRepository extends JpaRepository<Cat, Integer> {

}
