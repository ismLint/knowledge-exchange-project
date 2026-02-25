package org.knowledge.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "questions")
@Getter @Setter
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "quiz_id")
	private Quiz quiz;
	
	@Column(name = "question_text", columnDefinition = "TEXT")
	private String questionText;
	
	@Column(name = "question_type")
	private String questionType = "MULTIPLE_CHOICE";
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
	private List<Answer> answers;
	
}