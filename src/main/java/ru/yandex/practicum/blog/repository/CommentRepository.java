package ru.yandex.practicum.blog.repository;

import java.util.List;
import ru.yandex.practicum.blog.domain.Comment;

public interface CommentRepository {
  List<Comment> findAllByPostId(int postId);
  void deleteById(Integer id);
  void save(Comment comment);
  void update(Comment comment);
}
