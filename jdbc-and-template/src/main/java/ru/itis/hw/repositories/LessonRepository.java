package ru.itis.hw.repositories;

import ru.itis.hw.models.Lesson;

import java.util.List;

public interface LessonRepository extends CrudRepository<Long, Lesson> {
    List<Lesson> findByCourse(long id);
}
