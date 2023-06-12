package meatmeet.meatmeet.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Member;

@SpringBootTest
@Slf4j
class MemberRepositoryTest {
	@Autowired private MemberRepository memberRepository;

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
}