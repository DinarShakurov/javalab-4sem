package ru.shakurov.file_hosting_service.services;

import org.springframework.core.io.Resource;
import ru.shakurov.file_hosting_service.models.UploadFile;
import ru.shakurov.file_hosting_service.models.dto.UploadFileDto;

import java.util.Map;
import java.util.Optional;

public interface FilesService {
    Map<String, Object> upload(UploadFileDto uploadFileDto, String userEmail);

    Optional<Resource> loadAsResource(String filename);

    String getRealFileName(String filename);
}
