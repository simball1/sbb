package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

	private final QuestionRepository questionRepository;
	
	public Page<Question> getList(int page, String kw) {
//		return this.questionRepository.findAll().stream().map(QuestionDto::new).collect(Collectors.toList());
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		Specification<Question> spec = search(kw);
		return this.questionRepository.findAll(spec, pageable);

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
	
	private Specification<Question> search(String kw) {
		return new Specification<Question>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true); // 중복을 제거
				Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
				Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
				Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
				return cb.or(cb.like(q.get("subject"), "%" + kw + "%"),	//제목
						cb.like(q.get("content"), "%" + kw + "%"), 		// 내용
						cb.like(u1.get("username"), "%" + kw + "%"), 	// 질문 작성자
						cb.like(a.get("content"), "%" + kw + "%"), 		// 답변 내용
						cb.like(u2.get("username"), "%" + kw + "%")); 	// 답변작성자
			}
		};
	}
}
