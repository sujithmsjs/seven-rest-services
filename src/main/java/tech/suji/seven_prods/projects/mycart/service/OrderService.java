package tech.suji.seven_prods.projects.mycart.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tech.suji.seven_prods.projects.mycart.dto.OrderDTO;
import tech.suji.seven_prods.projects.mycart.entity.Order;
import tech.suji.seven_prods.projects.mycart.repo.OrderRepository;
import tech.suji.seven_prods.util.NotFoundException;


@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderDTO> findAll() {
        final List<Order> orders = orderRepository.findAll(Sort.by("id"));
        return orders.stream()
                .map(order -> mapToDTO(order, new OrderDTO()))
                .toList();
    }

    public OrderDTO get(final Long id) {
        return orderRepository.findById(id)
                .map(order -> mapToDTO(order, new OrderDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final OrderDTO orderDTO) {
        final Order order = new Order();
        mapToEntity(orderDTO, order);
        return orderRepository.save(order).getId();
    }

    public void update(final Long id, final OrderDTO orderDTO) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderDTO, order);
        orderRepository.save(order);
    }

    public void delete(final Long id) {
        orderRepository.deleteById(id);
    }

    private OrderDTO mapToDTO(final Order order, final OrderDTO orderDTO) {
        orderDTO.setId(order.getId());
        orderDTO.setQuantity(order.getQuantity());
        orderDTO.setGst(order.getGst());
        orderDTO.setAddress(order.getAddress());
        orderDTO.setCost(order.getCost());
        orderDTO.setStatus(order.getStatus());
        return orderDTO;
    }

    private Order mapToEntity(final OrderDTO orderDTO, final Order order) {
        order.setQuantity(orderDTO.getQuantity());
        order.setGst(orderDTO.getGst());
        order.setAddress(orderDTO.getAddress());
        order.setCost(orderDTO.getCost());
        order.setStatus(orderDTO.getStatus());
        return order;
    }

}
