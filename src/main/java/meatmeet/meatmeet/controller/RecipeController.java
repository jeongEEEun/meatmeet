package meatmeet.meatmeet.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Member;
import meatmeet.meatmeet.domain.Recipe;
import meatmeet.meatmeet.service.RecipeService;

@Slf4j
@Controller
public class RecipeController {
	private final RecipeService recipeService;
	
	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@GetMapping("/recipe")
	public String recipeAll(@SessionAttribute(required = false) Member member, Model model) {
		List<Recipe> recipes = recipeService.findAll();
        model.addAttribute("member", member);
        model.addAttribute("recipes", recipes);
		return "recipe/recipe";
	}
	
	@GetMapping("/recipe/{category1}/{category2}") 
	public String recipePart(@PathVariable String category1, @PathVariable String category2, @SessionAttribute(required = false) Member member, Model model) {
		List<Recipe> recipe = recipeService.findPart(category2);
		model.addAttribute("member", member);
		model.addAttribute("recipe", recipe);
		return "recipe/category";
	}
	
	@GetMapping("/recipe/{recipeId}") 
	public String detail(@PathVariable Long recipeId, @SessionAttribute(required = false) Member member, Model model){
		return "recipe/detail";
	}
	
	@GetMapping("/cart/{memberId}/{itemId}")
	public String cartAdd(@PathVariable String memberId, @PathVariable int itemId, @SessionAttribute Member member) {
		return "redirect:/recipe/{category1}/{category2}";
	}
	
}
