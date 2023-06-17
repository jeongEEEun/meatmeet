package meatmeet.meatmeet.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Cart;
import meatmeet.meatmeet.domain.Comment;
import meatmeet.meatmeet.domain.Member;

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
		
		recipeRepository.saveComment(comment);
		
		assertThat(comment.getComment()).isEqualTo("맛있어요");
	}
	
	@Test
	void findCommentByRecipeId() {
		Comment comment = new Comment(2, "ㅁ", "저장됨");
		
		recipeRepository.saveComment(comment);
		
		List<Comment> result = recipeRepository.findCommentByRecipeId(2L);
		
		log.info("RecipeRepositoryTest result >> {}", result);
		assertThat(result).isNotEmpty();
	}
}
