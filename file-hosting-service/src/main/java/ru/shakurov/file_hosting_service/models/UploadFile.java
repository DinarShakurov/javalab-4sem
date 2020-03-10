package ru.shakurov.file_hosting_service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadFile {
    private Integer id;
    private String name;
    private String extension;
    private String mimeType;
    private Long size;
    private String nameOnDisk;
    private String pathOnDisk;
    private String state;
}
