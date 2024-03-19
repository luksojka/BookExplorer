package com.project.bookexplorer.uploads.application;

import com.project.bookexplorer.uploads.application.port.UploadUseCase;
import com.project.bookexplorer.uploads.domain.Upload;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UploadService implements UploadUseCase {

    private final Map<String, Upload> storage = new ConcurrentHashMap<>();

    public Upload save(SaveUploadCommand command) {
        String newId = RandomStringUtils.randomAlphanumeric(10).toLowerCase();
        Upload upload = new Upload(
                newId,
                command.getFile(),
                command.getContentType(),
                command.getFilename(),
                LocalDateTime.now()
        );
        storage.put(upload.getId(), upload);
        System.out.println("Upload saved: " + upload.getFilename() + " with id: " + newId);
        return upload;
    }

    public Optional<Upload> getById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    public void removeById(String id) {
        storage.remove(id);
    }
}
