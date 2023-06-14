package meatmeet.meatmeet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import meatmeet.meatmeet.domain.Cart;
import meatmeet.meatmeet.domain.Item;
import meatmeet.meatmeet.domain.Recipe;
import meatmeet.meatmeet.repository.RecipeRepository;

@Service
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
	
	public int updateCnt(Long recipeId) {
		return recipeRepository.updateCnt(recipeId);
	}
//	
//	public Cart cartAdd(Cart cart) {
//		
//		return recipeRepository.cartAdd(cart);
//	}
//
	public Optional<Item> findItemById(int itemId) {
		return recipeRepository.findItemById(itemId);
	}
	public void cartAdd(Cart cart) {
	}
	
}
