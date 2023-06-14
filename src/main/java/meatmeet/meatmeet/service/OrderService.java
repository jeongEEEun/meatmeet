package meatmeet.meatmeet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import meatmeet.meatmeet.domain.Order;
import meatmeet.meatmeet.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Optional<Order> saveOrder(Order order) {
        // 주문 생성 로직 구현
        return orderRepository.save(order);
    }

    public List<Order> getOrderById(String memberId) {
        // 주문 조회 로직 구현
        return orderRepository.findByMemberId(memberId);
    }


<<<<<<< HEAD
//    public void processPayment(Long orderId, String paymentMethod) {
//        // 결제 처리 로직 구현
//        Optional<Order> optionalOrder = orderRepository.findByOrderId(orderId);
//        if (optionalOrder.isPresent()) {
//            Order order = optionalOrder.get();
//            order.setPayment(paymentMethod);
//            orderRepository.save(order);
//        } else {
//            // 주문을 찾을 수 없는 경우 예외 처리 로직 작성
//            // 예: throw new OrderNotFoundException("주문을 찾을 수 없습니다.");
//        }
//    }
    
//    public void processPayment(Long orderId, String paymentMethod) {
//        // 결제 처리 로직 구현
//        Order order = orderRepository.findById(orderId);
//        order.setPayment(paymentMethod);
//        orderRepository.save(order);
//    }
//
//    public void cancelOrder(Long orderId) {
//        orderRepository.deleteById(orderId);
//    }
=======
    public void cancelOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

>>>>>>> 7df70b3d936f78d073ce542632fd06502a1d96a6
}