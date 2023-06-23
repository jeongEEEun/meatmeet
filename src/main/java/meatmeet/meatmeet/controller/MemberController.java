package meatmeet.meatmeet.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Member;
import meatmeet.meatmeet.domain.Recipe;
import meatmeet.meatmeet.service.MemberService;
import meatmeet.meatmeet.service.RecipeService;

@Controller
@Slf4j
public class MemberController {
	private final MemberService memberService;
	private final RecipeService recipeService;

	public MemberController(MemberService memberService, RecipeService recipeService) {
		this.memberService = memberService;
		this.recipeService = recipeService;
	}

	@GetMapping("/sign-up")
	public String signUpForm() {
		return "member/sign-up";
	}

	@PostMapping("/sign-up")
	public String signUp(@ModelAttribute Member member, Model model) {
		Optional<Member> savedMember = memberService.saveMember(member);
		if (savedMember.isEmpty()) {
	        model.addAttribute("duplicateId", true); 
	        return "member/sign-up";
	    }
		return "redirect:/sign-in";
	}

	@GetMapping("/sign-in")
	public String signInForm() {
		return "member/sign-in";
	}

	@PostMapping("/sign-in")
	public String signIn(Member member, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		Optional<Member> loginMember = memberService.login(member);

		if (loginMember.isEmpty()) {
			return "redirect:/sign-in";
		}

		log.info("[MemberContoller - signIn()] 로그인 >> " + member.getMemberId());
		session.setAttribute("member", loginMember.get());
		return "redirect:/";
	}

	@GetMapping("/sign-out")
	public String signOut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("/recipe/{memberId}/new")
	public String newRecipeForm(@PathVariable String memberId, @SessionAttribute Member member, Model model) {
		model.addAttribute("member", member);
		return "recipe/new";
	}
	
	@PostMapping("/recipe/{memberId}/new")
	public String newRecipe(@PathVariable String memberId, @SessionAttribute Member member, 
			@RequestParam MultipartFile imgFile, Recipe recipe, RedirectAttributes redirectAttributes) throws Exception {

		Long recipeId = memberService.saveRecipe(imgFile, recipe);
		redirectAttributes.addAttribute("recipeId", recipeId);
		return "redirect:/recipe/{recipeId}";
	}

	@GetMapping("/myrecipe/{memberId}")
	public String myRecipe(@PathVariable String memberId, @SessionAttribute Member member, Model model) {
		List<Recipe> myRecipe = memberService.findRecipeByMemberId(memberId);
		
		model.addAttribute("member", member);
		model.addAttribute("myRecipe", myRecipe);

		return "recipe/myrecipe";
	}

	@GetMapping("/recipe/{memberId}/{recipeId}/edit")
	public String recipeEditForm(@PathVariable String memberId, @PathVariable Long recipeId,
			@SessionAttribute Member member, Model model) {
		Optional<Recipe> findRecipe = memberService.findByRecipeId(memberId, recipeId);

		if (findRecipe.isPresent()) {
			model.addAttribute("recipe", findRecipe.get());
			model.addAttribute(member);
		}
		
		log.info("recipe.category1 >> {}", findRecipe.get().getCategory1());
		log.info("recipe.category2 >> {}", findRecipe.get().getCategory2());
		log.info("recipe.title >> {}", findRecipe.get().getTitle());
		
		return "recipe/edit";
	}

	@PostMapping("/recipe/{memberId}/{recipeId}/edit")
	public String recipeEdit(@PathVariable String memberId, @PathVariable Long recipeId,
			@SessionAttribute Member member, Recipe recipe, RedirectAttributes redirectAttributes) {
		recipe.setRecipeId(recipeId);
		memberService.updateRecipe(recipe);

		redirectAttributes.addAttribute("recipeId", recipeId);

		return "redirect:/recipe/{recipeId}";
	}

	@GetMapping("/recipe/{memberId}/{recipeId}/delete")
	public String recipeDelete(@PathVariable String memberId, @PathVariable Long recipeId,
			@SessionAttribute Member member) {
		
		recipeService.deleteCommentsByRecipeId(recipeId);
		memberService.deleteRecipe(memberId, recipeId);
		
		return "redirect:/myrecipe/" + memberId;
	}
}