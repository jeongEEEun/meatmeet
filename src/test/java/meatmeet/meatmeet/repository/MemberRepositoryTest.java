package meatmeet.meatmeet.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Member;
import meatmeet.meatmeet.domain.Recipe;

@SpringBootTest
@Slf4j
class MemberRepositoryTest {
	@Autowired private MemberRepository memberRepository;
	@Autowired private RecipeRepository recipeRepository;

	@Test
	void findByMetmberId() {
		Member member = new Member("홍길동", "a@a.com", "hong", "1234");
		memberRepository.saveMember(member);
		
		Optional<Member> result = memberRepository.findByMemberId("hong");
		
		assertThat(result).isNotEmpty();
	}
	
	@Test
	void saveMember() {
		Member member = new Member("박보검", "b@b.com", "park", "5678");
		
		Optional<Member> result = memberRepository.saveMember(member);
		
		assertThat(result).isNotEmpty();
	}
	
	@Test
	void saveRecipe() {
		int beforeSize = recipeRepository.findAll().size();
		
		Member member = new Member("테스트", "a@a.com", "test", "1111");
		Optional<Member> savedMember = memberRepository.saveMember(member);
		Recipe recipe = new Recipe(savedMember.get().getMemberId(), "돼지", "목심", "제목", "재료", "양념", "만드는법");
		recipe.setImgName("img.jpg");
		recipe.setImgPath("img/img.jpg");
		
		Long result = memberRepository.saveRecipe(recipe);
		
		int afterSize = recipeRepository.findAll().size();
		
		assertThat(afterSize - 1).isEqualTo(beforeSize);
	}
}