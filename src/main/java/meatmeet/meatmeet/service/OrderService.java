package meatmeet.meatmeet.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Cart;
import meatmeet.meatmeet.domain.Order;
import meatmeet.meatmeet.repository.CartRepository;
import meatmeet.meatmeet.repository.OrderRepository;

@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    public List<Order> saveOrder(Order order) {
        log.info("[service] 주문자 >>" + order.getUserName());
    	
    	List<Cart> cartItems = cartRepository.findCartByMemberId(order.getMemberId());
    	List<Order> orders = new ArrayList<>();
    	
    	LocalDate today = LocalDate.now();
    	
    	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
    	String orderId = sdf.format(timestamp);
    	
    	for(Cart cart: cartItems) {
    		Order orderItem = order;
    		orderItem.setOrderId(orderId);
    		orderItem.setMemberId(cart.getMemberId());
    		orderItem.setItemId(cart.getItemId());
    		orderItem.setItemName(cart.getItemName());
    		orderItem.setPrice(cart.getPrice());
    		orderItem.setQuantity(cart.getQuantity());
    		orderItem.setOrderDate(today);
    		
    		orders.add(orderItem);
    	}
    	
    	orderRepository.save(orders);
    	
    	return orders;
    }

    public List<Order> getOrderById(String memberId) {
        return orderRepository.findByMemberId(memberId);
    }

    public void cancelOrder(Long orderId) {
        orderRepository.deleteByOrderId(orderId);
    }
}