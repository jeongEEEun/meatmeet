package meatmeet.meatmeet.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Member;
import meatmeet.meatmeet.domain.Recipe;
import meatmeet.meatmeet.repository.MemberRepository;

@Service
@Slf4j
public class MemberService {
	private final MemberRepository memberRepository;
	private final S3Uploader s3Uploader;
	
	public MemberService(MemberRepository memberRepository, S3Uploader s3Uploader) {
		this.memberRepository = memberRepository;
		this.s3Uploader = s3Uploader;
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
		
		if(findMember.isPresent() && findMember.get().getMemberPw().equals(member.getMemberPw())) {
			return findMember;
		}
		
		return Optional.empty();
	}

	// 글 작성 -> 저장
	public Long saveRecipe(MultipartFile imgFile, Recipe recipe) throws IOException {
		if(!imgFile.isEmpty()) {
			String storedFileName = s3Uploader.upload(imgFile, "images");
			recipe.setImgPath(storedFileName);
		}
		return memberRepository.saveRecipe(recipe); 
	}
	
	public List<Recipe> findRecipeByMemberId(String memberId) {
		return memberRepository.findRecipeByMemberId(memberId);
	}
	
	public Optional<Recipe> findByRecipeId(String memberId, Long recipeId) {
		List<Recipe> recipe = memberRepository.findRecipeByMemberId(memberId);
		return recipe.stream().filter(r -> r.getRecipeId() == recipeId).findFirst();
	}
	
	public void updateRecipe(Recipe recipe) {
		memberRepository.updateRecipe(recipe);
	}
	
	public void deleteRecipe(Long recipeId) {
		memberRepository.deleteRecipe(recipeId);
	}
}
