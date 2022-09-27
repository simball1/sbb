package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {

	private final AnswerRepository answerRepository;
	private final QuestionRepository questionRepository;

	public void create(Integer questionId, String content, SiteUser author) {
		
		Optional<Question> oq = this.questionRepository.findById(questionId);
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(oq.get());
		answer.setAuthor(author);
		this.answerRepository.save(answer);

	}
}
