package ru.shakurov.file_hosting_service.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.shakurov.file_hosting_service.models.UploadFile;

import ru.shakurov.file_hosting_service.repositories.FilesRepository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class FilesRepositoryJdbcTemplateImpl implements FilesRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //language=PostgreSQL
    private static final String SQL_INSERT = "insert into file (name, extension, mime_type, size, name_on_disk, path_on_disk, state) values (?,?,?,?,?,?,?)";
    //language=PostgreSQL
    private static final String SQL_FIND_BY_PATH_ON_DISK = "select * from file where name_on_disk = ?";

    private RowMapper<UploadFile> uploadFileRowMapper = (rs, rowNum) ->
            UploadFile.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .extension(rs.getString("extension"))
                    .mimeType(rs.getString("mime_type"))
                    .size(rs.getLong("size"))
                    .nameOnDisk(rs.getString("name_on_disk"))
                    .pathOnDisk(rs.getString("path_on_disk"))
                    .state(rs.getString("state"))
                    .build();


    @Override
    public Optional<UploadFile> find(Integer integer) {
        return Optional.empty();
    }

    @Override
    public List<UploadFile> findAll() {
        return null;
    }

    @Override
    public void save(UploadFile entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_INSERT);
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getExtension());
            ps.setString(3, entity.getMimeType());
            ps.setLong(4, entity.getSize());
            ps.setString(5, entity.getNameOnDisk());
            ps.setString(6, entity.getPathOnDisk());
            ps.setString(7, entity.getState());
            return ps;
        }, keyHolder);
        entity.setId((Integer) keyHolder.getKey());
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public Optional<UploadFile> findByNameOnDisk(String filename) {
        try {
            UploadFile uploadFile = jdbcTemplate.queryForObject(SQL_FIND_BY_PATH_ON_DISK, uploadFileRowMapper, filename);
            return Optional.ofNullable(uploadFile);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
