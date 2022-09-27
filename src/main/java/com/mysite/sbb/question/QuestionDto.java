package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionDto {

	private Integer id;
	private String subject;
	private String content;
	private LocalDateTime createDate;
	private List<Answer> answerList;
	private SiteUser author;
	
	public QuestionDto(Question question) {
		this.id = question.getId();
		this.subject = question.getSubject();
		this.content = question.getContent();
		this.createDate = question.getCreateDate();
		this.answerList = question.getAnswerList();
		this.author = question.getAuthor();
	}
}
