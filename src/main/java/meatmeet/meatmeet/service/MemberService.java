package meatmeet.meatmeet.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Member;
import meatmeet.meatmeet.repository.MemberRepository;

@Service
@Slf4j
public class MemberService {
	private final MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	// 회원가입
	// id로 조회 후 존재하면 empty 리턴
	public Optional<Member> saveMember(Member member) {
		Optional<Member> findMember = memberRepository.findByMemberId(member.getMemberId());
		
		if(findMember.isEmpty()) {
			return memberRepository.saveMember(member);
		}
		
		return Optional.empty();
	}
	
	// 로그인
	// 비밀번호가 일치하지 않거나 id가 존재하지 않을 경우 empty 리턴
	public Optional<Member> login(Member member) {
		Optional<Member> findMember = memberRepository.findByMemberId(member.getMemberId());
		
		if(findMember.isEmpty()) {
			return Optional.empty();
		} else if(!member.getMemberPw().equals(findMember.get().getMemberPw())) {
			return Optional.empty();
		}
		
		return findMember;
	}
}
