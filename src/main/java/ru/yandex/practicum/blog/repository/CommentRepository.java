package ru.yandex.practicum.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.blog.db.model.DbComment;

public interface CommentRepository extends JpaRepository<DbComment, Integer> {

}
