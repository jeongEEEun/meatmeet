package meatmeet.meatmeet.repository;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Cart;
import meatmeet.meatmeet.domain.Comment;

@Slf4j
@SpringBootTest
public class RecipeRepositoryTest {
	@Autowired private RecipeRepository recipeRepository;
	
	@Test
	void addCart() {
		Cart cart = new Cart();
		cart.setMemberId("aa");
		cart.setItemId(1);
		cart.setItemName("안심");
		cart.setPrice(500);
		cart.setQuantity(2);
		
		recipeRepository.cartAdd(cart);
		
		assertThat(cart.getItemId()).isEqualTo(1);
		
	}
	
	@Test
	void saveComment() {
		Comment comment = new Comment(2, "aa", "맛있어요");
		
		Comment result = recipeRepository.saveComment(comment);
		
		log.info("saveComment result >> {}", result.getMemberId());
		assertThat(result).isNotNull();
	}
}
