package ru.itis.hw.repositories.template;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.hw.models.Lesson;
import ru.itis.hw.repositories.LessonRepository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class LessonRepositoryJdbcTemplateImpl implements LessonRepository {
    //language=PostgreSQL
    private static final String SQL_SELECT_ALL_BY_COURSE_ID = "select * from lesson where course_id = ?";
    //language=PostgreSQL
    private static final String SQL_SELECT_ALL = "select * from lesson";
    //language=PostgreSQL
    private static final String SQL_SELECT_BY_ID = "select * from lesson where id = ?";
    //language=PostgreSQL
    private static final String SQL_INSERT = "insert into lesson (title, course_id) values (?, ?)";
    //language=PostgreSQL
    private static final String SQL_DELETE_BY_ID = "delete from lesson where id = ?";

    private JdbcTemplate jdbcTemplate;

    public LessonRepositoryJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Lesson> findByCourse(long id) {
        return jdbcTemplate.query(SQL_SELECT_ALL_BY_COURSE_ID, new Object[]{id}, lessonRowMapper);
    }

    @Override
    public Optional<Lesson> find(Long id) {
        try {
            Lesson lesson = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[]{id}, lessonRowMapper);
            return Optional.ofNullable(lesson);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Lesson> fineAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, lessonRowMapper);
    }

    @Override
    public void save(Lesson entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_INSERT);
            ps.setString(1, entity.getTitle());
            ps.setLong(2, entity.getCourseId());
            return ps;
        }, keyHolder);
        entity.setCourseId((Long) keyHolder.getKey());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }

    private RowMapper<Lesson> lessonRowMapper = (rs, rowNum) ->
            Lesson.builder()
                    .id(rs.getLong("id"))
                    .title(rs.getString("title"))
                    .courseId(rs.getLong("course_id"))
                    .build();

}
