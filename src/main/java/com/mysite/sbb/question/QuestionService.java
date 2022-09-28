package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

	private final QuestionRepository questionRepository;
	
	public Page<Question> getList(int page) {
//		return this.questionRepository.findAll().stream().map(QuestionDto::new).collect(Collectors.toList());
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.questionRepository.findAll(pageable);

	}
	
	public QuestionDto getQuestion(Integer id) {
		Optional<Question> oq = this.questionRepository.findById(id);
		if(oq.isPresent()) {
			return new QuestionDto(oq.get());
		} else {
			throw new DataNotFoundException("question not found");
		}
	}
	
	public void create(String subject, String content, SiteUser author) {
		Question question = new Question();
		question.setSubject(subject);
		question.setContent(content);
		question.setCreateDate(LocalDateTime.now());
		question.setAuthor(author);
		this.questionRepository.save(question);
	}
	
	public void modify(QuestionDto questionDto, String subject, String content) {
		Question question = this.questionRepository.findById(questionDto.getId()).get();
		question.setSubject(subject);
		question.setContent(content);
		question.setModifyDate(LocalDateTime.now());
		this.questionRepository.save(question);
	}
	
	public void delete(QuestionDto questionDto) {
		Question question = this.questionRepository.findById(questionDto.getId()).get();
		this.questionRepository.delete(question);
	}
	
	public void vote(QuestionDto questionDto, SiteUser siteUser) {
		Question question = this.questionRepository.findById(questionDto.getId()).get();
		question.getVoter().add(siteUser);
		this.questionRepository.save(question);
	}
}
