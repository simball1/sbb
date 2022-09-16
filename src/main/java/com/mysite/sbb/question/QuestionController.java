package com.mysite.sbb.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String detail(Model model, @PathVariable("id") Integer id) {
		QuestionDto question = this.QuestionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";
	}
}
