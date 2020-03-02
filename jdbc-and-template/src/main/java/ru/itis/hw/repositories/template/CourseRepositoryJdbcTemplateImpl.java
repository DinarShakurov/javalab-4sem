package ru.itis.hw.repositories.template;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.hw.models.Course;
import ru.itis.hw.repositories.CourseRepository;
import ru.itis.hw.repositories.LessonRepository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class CourseRepositoryJdbcTemplateImpl implements CourseRepository {

    //language=PostgreSQL
    private static final String SQL_SELECT_BY_ID = "select * from course where id = ?";
    //language=PostgreSQL
    private static final String SQL_SELECT_ALL = "select * from course";
    //language=PostgreSQL
    private static final String SQL_INSERT = "insert into course (title) values (?)";
    //language=PostgreSQL
    private static final String SQL_DELETE_BY_ID = "delete from course where id = ?";

    private JdbcTemplate jdbcTemplate;
    private LessonRepository lessonRepository;

    public CourseRepositoryJdbcTemplateImpl(LessonRepository lessonRepository, JdbcTemplate jdbcTemplate) {
        this.lessonRepository = lessonRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Course> find(Long id) {
        try {
            Course course = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[]{id}, courseRowMapper);
            return Optional.ofNullable(course);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Course> fineAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, courseRowMapper);
    }

    @Override
    public void save(Course entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_INSERT);
            ps.setString(1, entity.getTitle());
            return ps;
        }, keyHolder);
        entity.setId((Long) keyHolder.getKey());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }

    private RowMapper<Course> courseRowMapper = (rs, rowNum) ->
            Course.builder()
                    .id(rs.getLong("id"))
                    .title(rs.getString("title"))
                    .lessons(lessonRepository.findByCourse(rs.getLong("id")))
                    .build();


}
