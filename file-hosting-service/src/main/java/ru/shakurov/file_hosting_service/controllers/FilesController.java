package ru.shakurov.file_hosting_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.shakurov.file_hosting_service.models.dto.UploadFileDto;
import ru.shakurov.file_hosting_service.services.FilesService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class FilesController {
    @Qualifier("proxied")
    @Autowired
    private FilesService filesService;

    @GetMapping(value = "/files")
    public ModelAndView openPage() {
        return new ModelAndView("files");
    }

    @PostMapping(value = "/files")
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile multipartFile, HttpSession httpSession) {
        String userEmail = (String) httpSession.getAttribute("user.email");
        String[] fullName = multipartFile.getOriginalFilename().split("\\.");

        UploadFileDto uploadFileDto = UploadFileDto.builder()
                .size(multipartFile.getSize())
                .multipartFile(multipartFile)
                .mimeType(multipartFile.getContentType())
                .name(fullName[0])
                .extension(fullName[1])
                .build();
        filesService.upload(uploadFileDto, userEmail);
        return new ModelAndView("redirect:/files");
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Optional<Resource> optionalResource = filesService.loadAsResource(filename);
        String fileName = filesService.getRealFileName(filename);
        return optionalResource
                .map(resource -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                        .body(resource))
                .orElseGet(() -> ResponseEntity.status(404).build());
    }
}
