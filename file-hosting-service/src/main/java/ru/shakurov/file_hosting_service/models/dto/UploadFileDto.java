package ru.shakurov.file_hosting_service.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileDto {
    private MultipartFile multipartFile;
    private String name;
    private String extension;
    private String mimeType;
    private Long size;
}
