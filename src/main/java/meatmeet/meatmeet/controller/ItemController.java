package meatmeet.meatmeet.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Item;
import meatmeet.meatmeet.domain.Member;
import meatmeet.meatmeet.service.ItemService;

@Controller
@Slf4j
public class ItemController {
	private final ItemService itemService;
	
	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}
	
	@GetMapping
	public String index(@SessionAttribute(required = false) Member member, Model model) {
		List<Item> items = itemService.findAllItem();
		
		Optional<Item> chicken = items.stream()
				.filter(i -> "닭".equals(i.getItemName()))
				.findAny();
		
		Optional<Item> egg = items.stream()
				.filter(i -> "계란".equals(i.getItemName()))
				.findAny();
		
		Optional<Item> milk = items.stream()
				.filter(i -> "우유".equals(i.getItemName()))
				.findAny();
		
		model.addAttribute("member", member);
		model.addAttribute("items", items);
		model.addAttribute("chicken", chicken.get());
		model.addAttribute("egg", egg.get());
		model.addAttribute("milk", milk.get());
		
		return "index";
	}
	
	@GetMapping("/update-item-price")
	public String updateItemPrice() throws IOException {
		itemService.readCsv();
		return "/";
	}
}
