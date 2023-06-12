package meatmeet.meatmeet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import meatmeet.meatmeet.domain.Member;
import meatmeet.meatmeet.service.RecipeService;

@Controller
public class RecipeController {
	private final RecipeService recipeService;
	
	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@GetMapping("/recipe")
	public String recipe(@SessionAttribute(required = false) Member member, Model model) {
		model.addAttribute("member", member);
		return "recipe/recipe";
		
	}
}
