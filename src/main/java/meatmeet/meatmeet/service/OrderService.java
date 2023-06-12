package meatmeet.meatmeet.service;

import org.springframework.stereotype.Service;

import meatmeet.meatmeet.repository.OrderRepository;

@Service
public class OrderService {
	private final OrderRepository orderRepository;
	
	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
}
