package ru.yandex.practicum.blog.db.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.blog.db.model.DbTag;

@Repository
public interface TagRepository extends JpaRepository<DbTag, Integer> {
  void deleteAllByPostId(int tagId);
  List<DbTag> findAllByPostId(int postId);
}
