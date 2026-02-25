package org.knowledge.Repository;

import org.knowledge.Entity.Document;
import org.knowledge.Repository.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "http://localhost:5173") // request from React + Vite
public class DocumentController {
	
	private final DocumentService documentService;
	
	public DocumentController(DocumentService documentService) {
		this.documentService = documentService;
	}
	
	@PostMapping("/upload")
	public ResponseEntity<?> uploadDocument(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body("Файл не выбран. Выберите файл и повторите попытку");
		}
		
		try {
			Document savedDoc = documentService.uploadFile(file);
			// return saved object for frontend know id object
			return ResponseEntity.ok(savedDoc);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("Ошибка при загрузке: " + e.getMessage());
		}
		
	}
}