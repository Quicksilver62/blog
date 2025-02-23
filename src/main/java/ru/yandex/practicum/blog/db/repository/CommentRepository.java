package ru.yandex.practicum.blog.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.blog.db.model.DbComment;

@Repository
public interface CommentRepository extends JpaRepository<DbComment, Integer> {

}
