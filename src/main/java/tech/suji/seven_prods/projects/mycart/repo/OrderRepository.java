package tech.suji.seven_prods.projects.mycart.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.suji.seven_prods.projects.mycart.entity.Order;


public interface OrderRepository extends JpaRepository<Order, Long> {
}
