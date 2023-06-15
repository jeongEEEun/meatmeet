package meatmeet.meatmeet.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Member;
import meatmeet.meatmeet.domain.Order;
import meatmeet.meatmeet.service.OrderService;


@Controller
@Slf4j
public class OrderController {
    private final OrderService orderService;
    
    public OrderController(OrderService orderService) {
    	this.orderService = orderService;
    }
    
    @GetMapping("/neworder/{memberId}")
    public String orderForm(@PathVariable String memberId, @SessionAttribute Member member, Model model) {
        // 주문 페이지를 렌더링하는 로직을 구현합니다.
    	model.addAttribute("member", member);
        return "order/order";
    }
    
    @PostMapping("/neworder/{memberId}")
    public String submitOrder(@PathVariable String memberId, @SessionAttribute Member member, Order order, Model model, RedirectAttributes redirectAttributes) {
        orderService.saveOrder(order);

        log.info("[controller] 주문자 >>" + order.getUserName());
         
        model.addAttribute("member", member);
        redirectAttributes.addAttribute("memberId", memberId);
        
        return "redirect:/order/{memberId}";
    }
//    
    @GetMapping("/order/{memberId}")
    public String orderList(@PathVariable String memberId, Model model) {
        // 주문 내역을 조회하여 주문 목록 페이지를 렌더링하는 로직을 구현합니다.
        List<Order> orderList = orderService.getOrderById(memberId);
        model.addAttribute("orderList", orderList);
        return "order/order-list";
    }
}