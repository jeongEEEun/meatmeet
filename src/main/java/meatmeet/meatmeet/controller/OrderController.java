package meatmeet.meatmeet.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Member;
import meatmeet.meatmeet.domain.Order;
import meatmeet.meatmeet.service.CartService;
import meatmeet.meatmeet.service.OrderService;


@Controller
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;
    
    public OrderController(OrderService orderService, CartService cartService) {
    	this.orderService = orderService;
    	this.cartService = cartService;
    }
    
    @GetMapping("/neworder/{memberId}")
    public String orderForm(@PathVariable String memberId, @SessionAttribute Member member, Model model) {
    	int itemPrice = cartService.totalPrice(memberId);
    	
    	model.addAttribute("member", member);
    	model.addAttribute("itemPrice", itemPrice + "원");
    	model.addAttribute("totalPrice", itemPrice + 3000 + "원");
    	
        return "order/order";
    }
    
    @PostMapping("/neworder/{memberId}")
    public String order(@PathVariable String memberId, @SessionAttribute Member member, Order order, Model model, RedirectAttributes redirectAttributes) {
        orderService.saveOrder(order);
        
        log.info("[Ordercontroller - order] 주문자 >>" + order.getUserName());	
        
        model.addAttribute("member", member);
        redirectAttributes.addAttribute("memberId", memberId);
        
        return "redirect:/order/{memberId}";
    }
    
    @GetMapping("/order/{memberId}")
    public String orderList(@PathVariable String memberId, @SessionAttribute Member member , Model model) {
    	List<Order> orderInfo = orderService.findOrderInfoByMemberId(memberId);
    	List<Order> orderItems = orderService.findOrderItemByMemberId(memberId);
    	
    	Collections.reverse(orderInfo);
    	
    	model.addAttribute("member", member);
    	model.addAttribute("orderInfo", orderInfo);
    	model.addAttribute("orderItems", orderItems);
    	
        return "order/order-list";
    }
    
    @GetMapping("/order/{memberId}/{orderId}/cancel") 
    public String cancelOrder(@PathVariable String memberId, @PathVariable String orderId, @SessionAttribute Member member) {
    	log.info("주문 삭제 >> " + orderId);
    	orderService.deleteOrder(orderId);
    	return "redirect:/order/" + memberId;
    }
}