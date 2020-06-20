package ru.itis.springbootdemo.service;

import ru.itis.springbootdemo.dto.InformationDto;

public interface FilesService {
    void init();

    void convert();

    void convertPngByUser(Long userId);

    InformationDto getInformation(Long userId);
}
