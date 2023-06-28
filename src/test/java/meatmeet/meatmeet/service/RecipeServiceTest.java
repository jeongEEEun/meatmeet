package meatmeet.meatmeet.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import meatmeet.meatmeet.domain.Cart;
import meatmeet.meatmeet.domain.Comment;

@SpringBootTest
public class RecipeServiceTest {
	@Autowired private RecipeService recipeService;
	
	@Test
	void addCart() {
		Cart cart = new Cart("min", 2, "우유", 100, 3);
		
		recipeService.cartAdd(cart);
		
		assertThat(cart.getItemName()).isEqualTo("우유");
	}
	
//	@Test
//	void saveComment() {
//		Comment comment = new Comment(3, "min", "나중에 해먹고 싶어요");
//		recipeService.saveComment(comment);
//		
//		assertThat(comment.getRecipeId()).isEqualTo(3);
//	}

}
