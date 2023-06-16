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
    	
    	// 주문날짜
    	LocalDate today = LocalDate.now();
    	
    	// 주문번호
    	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
    	String orderId = sdf.format(timestamp);
    	
    	for(Cart cart: cartItems) {
    		Order orderItem = new Order(order);
    		
    		orderItem.setOrderId(orderId);
    		orderItem.setMemberId(cart.getMemberId());
    		orderItem.setItemId(cart.getItemId());
    		orderItem.setPrice(cart.getPrice());
    		orderItem.setTotalPrice(cartRepository.totalPrice(order.getMemberId()));
    		orderItem.setQuantity(cart.getQuantity());
    		orderItem.setOrderDate(today);
    		
    		orders.add(orderItem);
    	}
    	
    	orderRepository.saveOrder(orders);
    	cartRepository.resetCart(order.getMemberId());
    }

    public List<Order> findByMemberId(String memberId) {
    	List<Order> orders = orderRepository.findByMemberId(memberId); 

    	for(Order order: orders) {
    		Optional<Item> item = cartRepository.findByItemId(order.getItemId());
    		
    		if(item.isPresent()) {
    			order.setItemName(item.get().getItemName());
    		}
    	}
        return orders;
    }

    public void deleteByOrderId(Long orderId) {
        orderRepository.deleteByOrderId(orderId);
    }
}