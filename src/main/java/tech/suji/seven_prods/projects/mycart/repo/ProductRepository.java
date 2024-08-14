package tech.suji.seven_prods.projects.mycart.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.suji.seven_prods.projects.mycart.entity.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {
}
