package ru.yandex.practicum.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.blog.db.model.DbTag;

public interface TagRepository extends JpaRepository<DbTag, Integer> {

}
