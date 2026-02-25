package org.knowledge.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Document {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private User owner;
	
	@Column(nullable = false)
	private String title;
	
	@Column(name = "file_path", nullable = false)
	private String filePath;
	
	@Column(name = "file_type")
	private String fileType;
	
	@Column(length = 20)
	private String status = "PROCESSING";
	
	@Column(name = "created_at")
	private LocalDateTime created_at = LocalDateTime.now();
}