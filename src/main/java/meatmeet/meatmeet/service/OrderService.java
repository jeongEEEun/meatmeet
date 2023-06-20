package meatmeet.meatmeet.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Cart;
import meatmeet.meatmeet.domain.Item;
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

    public void saveOrder(Order order) {
    	List<Cart> cartItems = cartRepository.findCartByMemberId(order.getMemberId());
    	List<Order> orders = new ArrayList<>();
    	
    	// 주문번호
    	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
    	String orderId = sdf.format(timestamp);
    	
    	order.setOrderId(orderId);
    	order.setOrderDate(LocalDate.now());
    	order.setTotalPrice(cartRepository.totalPrice(order.getMemberId()));
    	
    	log.info("[service] orderId >> " + orderId);
    	log.info("[service] OrderDate >> " + order.getOrderDate());
    	log.info("[service] OrderAddress >> " + order.getAddress());
    	
    	for(Cart cart: cartItems) {
    		Order orderItem = new Order(order);
    		
    		orderItem.setItemId(cart.getItemId());
    		orderItem.setItemName(cart.getItemName());
    		orderItem.setQuantity(cart.getQuantity());
    		orderItem.setPrice(cart.getPrice());
    		
    		orders.add(orderItem);
    	}
    	
    	orderRepository.saveOrder(orders);
    }

    public List<Order> findOrderInfoByMemberId(String memberId) {
    	return orderRepository.findByMemberId(memberId);
    }
    
    public List<Order> findOrderItemByMemberId(String memberId) {
    	List<Order> orderInfo = orderRepository.findByMemberId(memberId);
    	List<Order> orderItems = new ArrayList<>();
    	
    	for(Order order: orderInfo) {
    		List<Order> findOrderItems = orderRepository.findByOrderId(order.getOrderId());
    		orderItems.addAll(findOrderItems);
    	}
    	return orderItems;
    }
    
    public void deleteOrder(String orderId) {
    	orderRepository.deleteOrder(orderId);
    }
}