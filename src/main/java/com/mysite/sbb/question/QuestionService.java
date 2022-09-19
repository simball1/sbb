package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

	private final QuestionRepository questionRepository;
	
	public List<QuestionDto> getList() {
		return this.questionRepository.findAll().stream().map(QuestionDto::new).collect(Collectors.toList());
	}
	
	public QuestionDto getQuestion(Integer id) {
		Optional<Question> oq = this.questionRepository.findById(id);
		if(oq.isPresent()) {
			return new QuestionDto(oq.get());
		} else {
			throw new DataNotFoundException("question not found");
		}
	}
	
	public void create(String subject, String content) {
		Question question = new Question();
		question.setSubject(subject);
		question.setContent(content);
		question.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(question);
	}
}
