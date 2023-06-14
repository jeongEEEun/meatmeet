package meatmeet.meatmeet.repository;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Cart;

@Slf4j
@SpringBootTest
public class RecipeRepositoryTest {
	@Autowired private RecipeRepository recipeRepository;
	
//	@Test
//	void saveCart() {
//		Cart cart = new Cart("aa", 1, 1);
//		
//		int result = recipeRepository.addCart(cart.getItemId(), cart.getMemberId());
//		
//		log.info("result >> {}", result);
//		log.info("itemid >> {}", cart.getItemId());
//		assertThat(result).isNotNull();
//	}
}
