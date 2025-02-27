package ru.yandex.practicum.blog.repository;

import java.util.List;
import ru.yandex.practicum.blog.domain.Tag;

public interface TagRepository {

  List<Tag> findAllByPostId(int postId);
  void deleteAllByPostId(int postId);
  void saveAll(List<Tag> tags);
}
