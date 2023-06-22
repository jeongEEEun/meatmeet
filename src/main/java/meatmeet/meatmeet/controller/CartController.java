package meatmeet.meatmeet.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Cart;
import meatmeet.meatmeet.domain.Member;
import meatmeet.meatmeet.service.CartService;

@Controller
@Slf4j
public class CartController {
	private final CartService cartService;
	
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}
	
	@GetMapping("/cart/{memberId}")
	public String showCart(@PathVariable String memberId, @SessionAttribute Member member ,Model model) {
		List<Cart> cartItems = cartService.findCartByMemberId(memberId);
		int itemPrice = cartService.totalPrice(memberId);
		
		model.addAttribute("member", member);
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("itemPrice", itemPrice + "원");
		model.addAttribute("totalPrice", itemPrice + 3000 + "원");
		
		return "order/cart";
	}
	
	@GetMapping("/cart/{memberId}/{itemId}/{quantity}")
	public String updateItemQuantity(@PathVariable String memberId, @PathVariable int itemId, @PathVariable int quantity,
			RedirectAttributes redirectAttributes) {
		
		cartService.updateQuantity(memberId, itemId, quantity);
		redirectAttributes.addAttribute("memberId", memberId);
		
		return "redirect:/cart/{memberId}";
	}
}
