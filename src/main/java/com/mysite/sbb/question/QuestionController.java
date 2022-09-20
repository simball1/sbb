package com.mysite.sbb.question;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.sbb.answer.AnswerForm;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/question")
@Controller
public class QuestionController {

	private final QuestionService QuestionService;
	
	@RequestMapping("/list")
	public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
		Page<Question> paging = this.QuestionService.getList(page);
		model.addAttribute("paging", paging);
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
