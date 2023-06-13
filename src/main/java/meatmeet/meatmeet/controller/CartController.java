package meatmeet.meatmeet.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Item;
import meatmeet.meatmeet.service.CartService;

@Controller
@Slf4j
public class CartController {
	private final CartService cartService;
	
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}
	
	
	// 장바구니 조회
	@GetMapping("/cart/{memberId}")
	public String cart(@PathVariable("memberId") String memberId, Model model) {
	    List<Item> itemNameList = cartService.getItemNameByMemberId(memberId);
	    List<Item> itemPriceList = cartService.getItemPrice(memberId);

	    model.addAttribute("itemNameList", itemNameList);
	    model.addAttribute("itemPriceList", itemPriceList);

	    
	    return "order/cart.html";
	}

	
//	장바구니 상품 추가
	@GetMapping("/cart/{memberId}/{itemId}")
	public String addCart() {
		
		return "redirect:/recepi/{items}/{item}";
	}
	
//	수량변경
	@PostMapping("/cart/{memberId}/{itemId}/{quantity}")
	public String updateCartQuantity() {
		
		return "redircet:/cart/{memberId}";
	}
}
