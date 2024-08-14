package tech.suji.seven_prods.projects.mycart.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import tech.suji.seven_prods.projects.mycart.entity.Cart;
import tech.suji.seven_prods.projects.mycart.entity.Product;


public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByProduct(Product product);

}
