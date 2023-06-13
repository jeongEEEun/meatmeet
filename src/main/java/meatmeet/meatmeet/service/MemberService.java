package meatmeet.meatmeet.service;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Member;
import meatmeet.meatmeet.domain.Recipe;
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
		
		if(findMember.isPresent()) {
			return findMember;
		}
		
		return Optional.empty();
	}
	
	// 글 작성 -> 저장
	public Long saveRecipe(Recipe recipe, MultipartFile imgFile) throws Exception {
		log.info("[MemberService - saveRecipe] 저장");
		
		// 기존 파일명 변수에 저장, 
		String originImgName = imgFile.getOriginalFilename();
		
		// 파일 저장 경로
		String imgFilePath = System.getProperty("user.dir") + "/src/main/resources/static/img/recipe-img/";
		
		// 파일명 중복 방지
		// 현재 시간을 기준으로 난수화시킨 이름 + 기존 파일명
		UUID uuid = UUID.randomUUID();
		String saveFileName = uuid + "_" + originImgName;
		
		// 만들어진 이름 imgName변수에 저장
		String imgName = saveFileName;
		
		// File 객체 생성(저장 경로 + 파일명)
		File saveFile = new File(imgFilePath, imgName);
		
		// 이미지 파일 저장
		imgFile.transferTo(saveFile);
		
		// 전달받은 recipe파일에 imgName, imgPath 저장
		recipe.setImgName(imgName);
		recipe.setImgPath("/img/recipe-img/" + imgName);
		
		log.info("[MemberService - saveRecipe] 파일명 >>" + recipe.getImgName());
		log.info("[MemberService - saveRecipe] 저장경로 >>" + recipe.getImgPath());
		
		// recipe 테이블에 저장 후 리턴받은 recipeId 리턴
		return memberRepository.saveRecipe(recipe);
	}
}
