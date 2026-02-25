package org.knowledge.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(name = "password_hash", nullable = false)
	private String passwordHash;
	
	@Column(length = 20)
	private String role = "STUDENT";
	
	@Column(name = "created_at")
	private LocalDateTime createdAt = LocalDateTime.now();
	
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
	private List<Document> documents;
}