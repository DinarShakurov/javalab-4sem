package ru.itis.hw;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.itis.hw.repositories.CourseRepository;
import ru.itis.hw.repositories.LessonRepository;
import ru.itis.hw.repositories.template.CourseRepositoryJdbcTemplateImpl;
import ru.itis.hw.repositories.template.LessonRepositoryJdbcTemplateImpl;

import javax.sql.DataSource;

public class AppWithTemplate {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/education_center";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "ifrehjdlbyfh";

    public static void main(String[] args) {
        DataSource dataSource = new DriverManagerDataSource(DB_URL, DB_USER, DB_PASSWORD);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        LessonRepository lessonRepository = new LessonRepositoryJdbcTemplateImpl(jdbcTemplate);
        CourseRepository courseRepository = new CourseRepositoryJdbcTemplateImpl(lessonRepository, jdbcTemplate);


    }
}
