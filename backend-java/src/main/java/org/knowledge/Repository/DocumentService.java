package org.knowledge.Repository;

import org.knowledge.Entity.Document;
import org.knowledge.Repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class DocumentService {

    @Value("${storage.location}")
    private String storageLocation;

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Document uploadFile(MultipartFile file) throws IOException {
        // create folder if not have
        Path root = Paths.get(storageLocation);
        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }

        // generate unique file name 
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path targetPath = root.resolve(fileName);

        // save data on disk
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        // write db
        Document doc = new Document();
        doc.setTitle(file.getOriginalFilename());
        doc.setFilePath(targetPath.toString());
        doc.setFileType(file.getContentType());
        doc.setStatus("PROCESSING");
        
        return documentRepository.save(doc);
    }
}