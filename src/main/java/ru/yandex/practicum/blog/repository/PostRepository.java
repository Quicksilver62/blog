package ru.yandex.practicum.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.blog.db.model.DbPost;

@Repository
public interface PostRepository extends JpaRepository<DbPost, Integer> {

}
