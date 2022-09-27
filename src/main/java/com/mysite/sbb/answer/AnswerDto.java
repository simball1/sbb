package com.mysite.sbb.answer;

import java.time.LocalDateTime;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDto {

	private Integer id;
	private String content;
	private LocalDateTime createDate;
	private Question question;
	private SiteUser author;
	private LocalDateTime modifyDate;
	
	public AnswerDto(Answer answer) {
		this.id = answer.getId();
		this.content = answer.getContent();
		this.createDate = answer.getCreateDate();
		this.question = answer.getQuestion();
		this.author = answer.getAuthor();
		this.modifyDate = answer.getModifyDate();
	}
}
