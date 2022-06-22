package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 질문 고유번호
	
	@Column(length = 200)
	private String subject; // 제목
	
	@Column(columnDefinition = "TEXT")
	private String content; // 내용
	
	private LocalDateTime createDate; // 생성일시
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList; // 답변목록
	
	@ManyToOne
	private SiteUser author; // 회원정보
	
	private LocalDateTime modifyDate; // 수정일시
	
	@ManyToMany
	Set<SiteUser> voter; // 추천인목록
}
