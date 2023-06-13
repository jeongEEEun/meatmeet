package meatmeet.meatmeet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;

import meatmeet.meatmeet.domain.Member;
import meatmeet.meatmeet.service.OrderService;


@Controller
public class OrderController {
	private final OrderService orderService;
	
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
//	@GetMapping
//	public String index(@SessionAttribute(required = false) Member member, Model model) {
//		model.addAttribute("member", member);
//		return "order";
//	}
	
	@GetMapping("/order")
	public String orderForm() {
		return "order/order";
	}
	
//	@GetMapping("/order")
//	public String order(@ModelAttribute Order order) {
//		orderService.saveOrder(order);
//		return "redict:/order";
//	}
//	
//	@GetMapping("/order-list")
//	public String orderList(@SessionAttribute(required = false) Member member, Model model) {
	    // 로그인한 회원 정보를 받아와서 필요한 작업을 수행하는 코드 작성
	    
//	    // 예시: 주문 목록을 가져와서 모델에 추가
//	    List<Order> orderList = orderService.getOrderList(member.getId());
//	    model.addAttribute("orderList", orderList);
//	    
//	    return "order/order-list"; // order-list.html 템플릿을 렌더링하여 응답
//	}

}
