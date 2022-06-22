package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 답변 고유번호
	
	@Column(columnDefinition = "TEXT")
	private String content; // 내용
	
	private LocalDateTime createDate; // 생성일시
	
	@ManyToOne
	private Question question; // 해당질문
	
	@ManyToOne
	private SiteUser author; // 회원정보
	
	private LocalDateTime modifyDate; // 수정일시
	
	@ManyToMany
	Set<SiteUser> voter; // 추천인목록

}
