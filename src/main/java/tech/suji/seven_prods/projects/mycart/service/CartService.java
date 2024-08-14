package tech.suji.seven_prods.projects.mycart.service;

import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tech.suji.seven_prods.projects.mycart.dto.CartDTO;
import tech.suji.seven_prods.projects.mycart.entity.Cart;
import tech.suji.seven_prods.projects.mycart.entity.Product;
import tech.suji.seven_prods.projects.mycart.repo.CartRepository;
import tech.suji.seven_prods.projects.mycart.repo.ProductRepository;
import tech.suji.seven_prods.util.NotFoundException;


@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(final CartRepository cartRepository,
            final ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public List<CartDTO> findAll() {
        final List<Cart> carts = cartRepository.findAll(Sort.by("id"));
        return carts.stream()
                .map(cart -> mapToDTO(cart, new CartDTO()))
                .toList();
    }

    public CartDTO get(final Long id) {
        return cartRepository.findById(id)
                .map(cart -> mapToDTO(cart, new CartDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CartDTO cartDTO) {
        final Cart cart = new Cart();
        mapToEntity(cartDTO, cart);
        return cartRepository.save(cart).getId();
    }

    public void update(final Long id, final CartDTO cartDTO) {
        final Cart cart = cartRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(cartDTO, cart);
        cartRepository.save(cart);
    }

    public void delete(final Long id) {
        cartRepository.deleteById(id);
    }

    private CartDTO mapToDTO(final Cart cart, final CartDTO cartDTO) {
        cartDTO.setId(cart.getId());
        cartDTO.setName(cart.getName());
        cartDTO.setQuantity(cart.getQuantity());
        cartDTO.setDate(cart.getDate());
        cartDTO.setProduct(cart.getProduct().stream()
                .map(product -> product.getId())
                .toList());
        return cartDTO;
    }

    private Cart mapToEntity(final CartDTO cartDTO, final Cart cart) {
        cart.setName(cartDTO.getName());
        cart.setQuantity(cartDTO.getQuantity());
        cart.setDate(cartDTO.getDate());
        final List<Product> product = productRepository.findAllById(
                cartDTO.getProduct() == null ? Collections.emptyList() : cartDTO.getProduct());
        if (product.size() != (cartDTO.getProduct() == null ? 0 : cartDTO.getProduct().size())) {
            throw new NotFoundException("one of product not found");
        }
        cart.setProduct(product.stream().collect(Collectors.toSet()));
        return cart;
    }

}
