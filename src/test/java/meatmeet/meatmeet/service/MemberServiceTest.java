package meatmeet.meatmeet.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import meatmeet.meatmeet.domain.Member;
import meatmeet.meatmeet.domain.Recipe;

@SpringBootTest
class MemberServiceTest {
	@Autowired private MemberService memberService;
	
	@Test
	void signUp() {
		Member memberOne = new Member("이미자", "abcd@gmail.com", "lee", "1111");
		memberService.saveMember(memberOne);
		
		Member memberTwo = new Member("이길동", "hong@gmail.com", "lee", "1234");		
		Optional<Member> result = memberService.saveMember(memberTwo);
		
		assertThat(result).isEmpty();
	}
}
