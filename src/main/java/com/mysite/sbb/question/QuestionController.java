package com.mysite.sbb.question;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.sbb.answer.AnswerForm;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/question")
@Controller
public class QuestionController {

	private final QuestionService QuestionService;
	
	@RequestMapping("/list")
	public String list(Model model) {
		List<QuestionDto> questionList = this.QuestionService.getList();
		model.addAttribute("questionList", questionList);
		return "question_list";
	}
	
	@RequestMapping("/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		QuestionDto question = this.QuestionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";
	}
	
	@GetMapping("/create")
	public String create(QuestionForm questionForm) {
		return "question_form";
	}
	
	@PostMapping("/create")
	public String create(@Valid QuestionForm questionForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "question_form";
		}
		this.QuestionService.create(questionForm.getSubject(), questionForm.getContent());
		return "redirect:/question/list";
	}
}
