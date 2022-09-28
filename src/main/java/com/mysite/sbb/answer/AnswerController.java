package com.mysite.sbb.answer;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

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
		AnswerDto answerDto = this.answerService.create(questionId, answerForm.getContent(), siteUser);
		return String.format("redirect:/question/detail/%s#answer_%s",
				answerDto.getQuestion().getId(), answerDto.getId() );
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String modify(AnswerForm answerForm,
			@PathVariable("id") Integer id, Principal principal) {
		AnswerDto answerDto = this.answerService.getAnswer(id);
		if(!answerDto.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		answerForm.setContent(answerDto.getContent());
		return "answer_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String modify(@Valid AnswerForm answerForm, BindingResult bindingResult,
			@PathVariable("id") Integer id, Principal principal) {
		if(bindingResult.hasErrors()) {
			return "answer_form";
		}
		AnswerDto answerDto = this.answerService.getAnswer(id);
		if(!answerDto.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		this.answerService.modify(answerDto, answerForm.getContent());
		return String.format("redirect:/question/detail/%s#answer_%s",
				answerDto.getQuestion().getId(), answerDto.getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String delete(Principal principal, @PathVariable("id") Integer id) {
		AnswerDto answerDto = this.answerService.getAnswer(id);
		if(!answerDto.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
		}
		this.answerService.delete(answerDto);
		return String.format("redirect:/question/detail/%s", answerDto.getQuestion().getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String vote(Principal principal, @PathVariable("id") Integer id) {
		AnswerDto answerDto = this.answerService.getAnswer(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.answerService.vote(answerDto, siteUser);
		return String.format("redirect:/question/detail/%s#answer_%s",
				answerDto.getQuestion().getId(), answerDto.getId());
	}
}
