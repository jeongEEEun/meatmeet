package meatmeet.meatmeet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import meatmeet.meatmeet.domain.Order;
import meatmeet.meatmeet.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

//    public List<Order> getAllOrders() {
//        return orderRepository.findAll();
//    }

    public List<Order> getOrdersByOrderName(String orderName) {
        return orderRepository.findByOrderName(orderName);
    }

    public void createOrder(Order order) {
        // 주문 생성 로직 구현
        orderRepository.save(order);
    }

    public Order getOrderById(Long orderId) {
        // 주문 조회 로직 구현
        return orderRepository.findById(orderId);
    }

    public void cancelOrder(Long orderId) {
        // 주문 취소 로직 구현
        Order order = orderRepository.findById(orderId);
        if (order != null) {
            // 주문 취소 상태로 변경
            order.setCancelled(true);
            orderRepository.save(order);
        }
    }
}
