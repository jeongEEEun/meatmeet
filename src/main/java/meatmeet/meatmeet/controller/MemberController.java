package meatmeet.meatmeet.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Member;
import meatmeet.meatmeet.service.MemberService;

@Controller
@Slf4j
public class MemberController {
	private final MemberService memberService;
	
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping
	public String index(@SessionAttribute(required = false) Member member, Model model) {
		model.addAttribute("member", member);
		return "index";
	}
	
	@GetMapping("/sign-up")
	public String signUpForm() {
		return "member/sign-up";
	}
	
	@PostMapping("/sign-up")
	public String signUp(@ModelAttribute Member member) {
		memberService.saveMember(member);
		return "redirect:/sign-in";
	}
	
	@GetMapping("/sign-in")
	public String signInForm() {
		return "member/sign-in";
	}
	
	@PostMapping("/sign-in")
	public String signIn(Member member, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Optional<Member> result = memberService.login(member); 
		
		if(result.isEmpty()) {
			return "redirect:/sign-in";
		}
		
		log.info("[MemberContoller - signIn()] 로그인 >> " + member.getMemberId());
		session.setAttribute("member", result.get());
		
		return "redirect:/";
	}
	
	@GetMapping("/sign-out")
	public String signOut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/";
	}
}