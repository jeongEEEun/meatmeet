package meatmeet.meatmeet.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Cart;
import meatmeet.meatmeet.domain.Comment;
import meatmeet.meatmeet.domain.Item;
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
		List<Item> items = recipeService.findItemAll();
        model.addAttribute("member", member);
        model.addAttribute("recipes", recipes);
        model.addAttribute("items", items);
		return "recipe/recipe";
	}
	
	@GetMapping("/recipe/{recipeId}") 
	public String detail(@PathVariable Long recipeId, @SessionAttribute(required = false) Member member, Model model){
		if (member == null) {
	        return "redirect:/sign-in";
	    }
		Optional<Recipe> details = recipeService.findRecipeById(member.getMemberId(), recipeId);
		model.addAttribute("recipeId", recipeId);
		model.addAttribute("details", details.get());
		recipeService.updateCnt(recipeId);
		return "recipe/detail";
	}
	
	@GetMapping("/cart/{memberId}/{itemId}")
	public String cartAdd(@PathVariable String memberId, @PathVariable int itemId, @SessionAttribute Member member) {
		Cart cart = new Cart();
		cart.setMemberId(memberId);
		cart.setItemId(itemId);
		Optional<Item> optionalItem = recipeService.findItemById(itemId);
	    if (optionalItem.isPresent()) {
	        Item item = optionalItem.get();
	        cart.setPrice(item.getTodayPrice()); 
	    }
	    boolean itemExists = recipeService.itemExist(memberId, itemId);
	    if (itemExists) {
	        return "redirect:/cart/" + memberId;
	    }
	    
	    recipeService.cartAdd(cart);
	    return "redirect:/recipe";
	}
	
	
	@PostMapping("/recipe/{recipeId}/comment")
	public String comment(@PathVariable("recipeId") Long recipeId, @SessionAttribute(required = false) Member member,
	        @RequestParam("commentText") String commentText, RedirectAttributes redirectAttributes, Model model) {
	    if (member == null) {
	        return "redirect:/sign-in";
	    }
		
	    Comment comment = new Comment();
	    
	    comment.setRecipeId(recipeId);
	    comment.setMemberId(member.getMemberId());
	    comment.setComment(commentText);
	    recipeService.saveComment(comment);
	    
	    redirectAttributes.addAttribute("recipeId", recipeId);
	    
	    List<Comment> comments = recipeService.findCommentByRecipeId(recipeId);
	    model.addAttribute("comments", comments);
	    
	    LocalDateTime currentTime = LocalDateTime.now();
	    model.addAttribute("currentTime", currentTime);
	    
	    log.info("시간 >> {}", currentTime);
	    log.info("findCommentByRecipeId >> {}", recipeService.findCommentByRecipeId(recipeId));
	    return "redirect:/recipe/{recipeId}";
	}
}
