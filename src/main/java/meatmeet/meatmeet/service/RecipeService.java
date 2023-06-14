package meatmeet.meatmeet.service;

import java.util.List;

import org.springframework.stereotype.Service;

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
	
}
