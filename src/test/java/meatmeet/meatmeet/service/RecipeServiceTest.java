package meatmeet.meatmeet.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import meatmeet.meatmeet.domain.Cart;
import meatmeet.meatmeet.domain.Comment;
import meatmeet.meatmeet.domain.Member;

@SpringBootTest
public class RecipeServiceTest {
	@Autowired private RecipeService recipeService;
	@Autowired private CartService cartService;
	@Autowired private MemberService memberService;
	
	@Test
	void addCart() {
		Member member = new Member("테스트", "abcd@gmail.com", "test1", "1111");
		memberService.saveMember(member);
		
		Cart cart = new Cart(member.getMemberId(), 1, 3);
		recipeService.cartAdd(cart);
		
		List<Cart> result = cartService.findCartByMemberId(member.getMemberId());
		
		assertThat(result.size()).isEqualTo(1);
	}
	
//	@Test
//	void saveComment() {
//		Comment comment = new Comment(3, "min", "나중에 해먹고 싶어요");
//		recipeService.saveComment(comment);
//		
//		assertThat(comment.getRecipeId()).isEqualTo(3);
//	}

}
