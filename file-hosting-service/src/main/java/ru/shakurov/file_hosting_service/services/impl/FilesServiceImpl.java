package ru.shakurov.file_hosting_service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.shakurov.file_hosting_service.models.UploadFile;
import ru.shakurov.file_hosting_service.models.dto.UploadFileDto;
import ru.shakurov.file_hosting_service.repositories.FilesRepository;
import ru.shakurov.file_hosting_service.services.FilesService;
import ru.shakurov.file_hosting_service.services.MailSender;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

@Component("default_FilesService")
public class FilesServiceImpl implements FilesService {

    @Autowired
    private FilesRepository filesRepository;
    @Autowired
    private Properties properties;

    private File dir;
    private Long count = 99L;

    @PostConstruct
    private void setDir(){
        this.dir = new File(properties.getProperty("storage.path"));
    }

    @Override
    public Map<String, Object> upload(UploadFileDto uploadFileDto, String userEmail) {
        count = Long.sum(count, 1L);
        try {
            File newFile = File.createTempFile(count.toString(), "." + uploadFileDto.getExtension(), dir);
            uploadFileDto.getMultipartFile().transferTo(newFile);
            UploadFile uploadFile = UploadFile.builder()
                    .name(uploadFileDto.getName())
                    .nameOnDisk(newFile.getName())
                    .extension(uploadFileDto.getExtension())
                    .size(uploadFileDto.getSize())
                    .mimeType(uploadFileDto.getMimeType())
                    .pathOnDisk(newFile.getAbsolutePath())
                    .state("READY")
                    .build();
            filesRepository.save(uploadFile);
            Map<String, Object> map = new HashMap<>();
            map.put("uploadFile", uploadFile);
            map.put("emailTo", userEmail);
            map.put("templateName", "email_download_link.ftlh");
            return  map;

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<Resource> loadAsResource(String filename) {
        Optional<UploadFile> optionalUploadFile = filesRepository.findByNameOnDisk(filename);
        if (optionalUploadFile.isPresent()) {
            UploadFile uploadFile = optionalUploadFile.get();
            return Optional.of(new FileSystemResource(uploadFile.getPathOnDisk()));
        }
        return Optional.empty();
    }

    @Override
    public String getRealFileName(String filename) {
        Optional<UploadFile> optionalUploadFile = filesRepository.findByNameOnDisk(filename);
        return optionalUploadFile
                .map(uploadFile -> uploadFile.getName() + "." + uploadFile.getExtension())
                .orElse(null);
    }
}
