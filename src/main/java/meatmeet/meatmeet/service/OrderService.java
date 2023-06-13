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

    public void processPayment(Long orderId, String paymentMethod) {
        // 결제 처리 로직 구현
        Order order = orderRepository.findById(orderId);
        order.setPayment(paymentMethod);
        orderRepository.save(order);
    }

    public void cancelOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

}