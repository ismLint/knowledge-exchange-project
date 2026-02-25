package org.knowledge.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "quizzes")
@Getter @Setter
@NoArgsConstructor
public class Quiz {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "document_id")
	private Document document;
	
	@Column(nullable = false)
	private String title;
	
	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
	private List<Question> questions;
}