package meatmeet.meatmeet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import meatmeet.meatmeet.domain.Member;
import meatmeet.meatmeet.domain.Order;
import meatmeet.meatmeet.service.OrderService;


@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    
    @GetMapping("/neworder/{memberId}")
    public String orderForm(@PathVariable String memberId, @SessionAttribute Member member, Model model) {
        // 주문 페이지를 렌더링하는 로직을 구현합니다.
    	model.addAttribute("member", member);
        return "order/order";
    }
    
    @PostMapping("/neworder/{memberId}")
    public String submitOrder(@PathVariable String memberId, @SessionAttribute Member member, Order order, RedirectAttributes redirectAttributes) {
        // 주문 내역을 저장하고 처리하는 로직을 구현합니다.
        orderService.saveOrder(order);
        return "redirect:/order-list";
    }
//    
    @GetMapping("/order/{memberId}")
    public String orderList(@PathVariable String memberId, Model model) {
        // 주문 내역을 조회하여 주문 목록 페이지를 렌더링하는 로직을 구현합니다.
        List<Order> orderList = orderService.getOrderById(memberId);
        model.addAttribute("orderList", orderList);
        return "order/order-list";
    }

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
//	@GetMapping
//	public String index(@SessionAttribute(required = false) Member member, Model model) {
//		model.addAttribute("member", member);
//		return "order";
//	}
	
//	@GetMapping("/order")
//	public String showOrderPage() {
//		return "order";
//	}
//    @PostMapping("/order")
//    public String createOrder(@ModelAttribute Order order) {
//        orderService.createOrder(order);
//        return "redirect:/order";
//    }
    
//    @GetMapping("/order-list")
//    public String showOrderListPage(Model model) {
//        // 주문 목록을 조회하여 모델에 추가
//        List<Order> orders = orderService.getAllOrders();
//        model.addAttribute("orders", orders);
//        
//        return "order-list";
//    }
    
//    @PostMapping("/cancel-order")
//    public String cancelOrder(@RequestParam("orderId") Long orderId) {
//        // 주문 취소 로직 수행
//        orderService.cancelOrder(orderId);
//        
//        return "redirect:/order-list";
//    }
    
//    주문 검색 기능
//    @GetMapping("/order/search")
//    public String searchOrders(@RequestParam("orderName") String orderName, Model model) {
//        model.addAttribute("orders", orderService.getOrdersByOrderName(orderName));
//        return "order";
//    }
}