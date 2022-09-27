package com.mysite.sbb.answer;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.sbb.question.QuestionDto;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

	private final AnswerService answerService;
	private final QuestionService quesitonService;
	private final UserService userService;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{id}")
	public String create(Model model, @PathVariable("id") Integer questionId,
			@Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
		if(bindingResult.hasErrors()) {
			QuestionDto question = this.quesitonService.getQuestion(questionId);
			model.addAttribute("question", question);
			return "question_detail";
		}
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.answerService.create(questionId, answerForm.getContent(), siteUser);
		return String.format("redirect:/question/detail/%s", questionId);
	}
}
