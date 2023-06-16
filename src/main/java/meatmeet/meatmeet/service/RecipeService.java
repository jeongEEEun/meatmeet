package meatmeet.meatmeet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Cart;
import meatmeet.meatmeet.domain.Comment;
import meatmeet.meatmeet.domain.Item;
import meatmeet.meatmeet.domain.Recipe;
import meatmeet.meatmeet.repository.RecipeRepository;

@Service
@Slf4j
public class RecipeService {
	private final RecipeRepository recipeRepository;
	
	public RecipeService(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}
	
	public List<Recipe> findAll() {
		return recipeRepository.findAll();
	}

	public List<Recipe> findType(String category1) {
		return recipeRepository.findType(category1);
	}

	public List<Recipe> findPart(String category2) {
		return recipeRepository.findPart(category2);
	}
	
	public Optional<Recipe> findRecipeById(String memberId, Long recipeId) {
		return recipeRepository.findRecipeById(recipeId);
	}
	
	public int updateCnt(Long recipeId) {
		return recipeRepository.updateCnt(recipeId);
	}

	public Optional<Item> findItemById(int itemId) {
		return recipeRepository.findItemById(itemId);
	}
	
	public List<Item> findItemAll() {
		return recipeRepository.findItemAll();
	}
	
	public void cartAdd(Cart cart) {
	    if (cart.getQuantity() <= 0) {
	        cart.setQuantity(1);
	    }
	    recipeRepository.cartAdd(cart);
	}
	
	public boolean itemExist(String memberId, int itemId) {
		return recipeRepository.itemExist(memberId, itemId);
	}
	
	public void saveComment(Comment comment) {
		log.info("service");
		recipeRepository.saveComment(comment);
	}
	
	public List<Comment> findCommentByRecipeId(Long reicpeId) {
		return recipeRepository.findCommentByRecipeId(reicpeId);
	}
}
