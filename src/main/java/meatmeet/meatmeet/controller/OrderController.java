package meatmeet.meatmeet.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import meatmeet.meatmeet.domain.Member;
import meatmeet.meatmeet.domain.Order;
import meatmeet.meatmeet.service.OrderService;


@Controller
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

//    @GetMapping("/order/{memberId}")
//    public String order(@PathVariable String memberId, Model model) {
//        Member member = orderService.getMemberById(memberId);
//
//        model.addAttribute("member", member);
//        model.addAttribute("order", new Order());
//
//        return "order";
//    }
//
//    @PostMapping("/order")
//    public String createOrder(@ModelAttribute Order order, @SessionAttribute Member member) {
//        orderService.createOrder(order);
//        return "redirect:/order/" + member.getId();
//    }

//    @GetMapping("/order")
//    public String order(@SessionAttribute(required = false) Member member, Model model) {
////        model.addAttribute("member", member);
////        model.addAttribute("order", new Order());
//    	return "order";
//    }

//    @PostMapping("/order")
//    public String createOrder(@ModelAttribute Order order) {
//        orderService.createOrder(order);
//        return "redirect:/order";
//    }

//    @GetMapping("/order-list")
//    public String showOrderListPage(Model model) {
//        List<Order> orders = orderService.getAllOrders();
//        model.addAttribute("orders", orders);
//        return "order-list";
//    }

    @PostMapping("/cancel-order")
    public String cancelOrder(@RequestParam("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/order-list";
    }

//    // 주문 검색 기능
//    @GetMapping("/order/search")
//    public String searchOrders(@RequestParam("orderName") String orderName, Model model) {
//        List<Order> orders = orderService.getOrdersByOrderName(orderName);
//        model.addAttribute("orders", orders);
//        return "order-list";
//    }
}
