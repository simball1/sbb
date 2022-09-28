package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {

	private final AnswerRepository answerRepository;
	private final QuestionRepository questionRepository;

	public AnswerDto create(Integer questionId, String content, SiteUser author) {
		Optional<Question> oq = this.questionRepository.findById(questionId);
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(oq.get());
		answer.setAuthor(author);
		this.answerRepository.save(answer);
		return new AnswerDto(answer);
	}
	
	public AnswerDto getAnswer(Integer id) {
		Optional<Answer> oa = this.answerRepository.findById(id);
		if(oa.isPresent()) {
			return new AnswerDto(oa.get());
		} else {
			throw new DataNotFoundException("answer not found");
		}
	}
	
	public void modify(AnswerDto answerDto, String content) {
		Answer answer = this.answerRepository.findById(answerDto.getId()).get();
		answer.setContent(content);
		answer.setModifyDate(LocalDateTime.now());
		this.answerRepository.save(answer);
	}
	
	public void delete(AnswerDto answerDto) {
		Answer answer = this.answerRepository.findById(answerDto.getId()).get();
		this.answerRepository.delete(answer);
	}
	
	public void vote(AnswerDto answerDto, SiteUser siteUser) {
		Answer answer = this.answerRepository.findById(answerDto.getId()).get();
		answer.getVoter().add(siteUser);
		this.answerRepository.save(answer);
	}
	
}
