package meatmeet.meatmeet.repository;

import meatmeet.meatmeet.domain.recipeDomain;

public interface recipeRepository {
	
	recipeDomain findByTitle(String title);
}
