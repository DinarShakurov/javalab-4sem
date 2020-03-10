package ru.shakurov.file_hosting_service.repositories;

import ru.shakurov.file_hosting_service.models.UploadFile;

import java.util.Optional;

public interface FilesRepository extends CrudRepository<Integer, UploadFile> {
    Optional<UploadFile> findByNameOnDisk(String filename);
}
