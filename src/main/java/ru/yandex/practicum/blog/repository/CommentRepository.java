package ru.yandex.practicum.blog.repository;

import java.util.List;
import java.util.Optional;
import ru.yandex.practicum.blog.domain.Comment;

public interface CommentRepository {
  List<Comment> findAllByPostId(int postId);
  Optional<Comment> findById(Integer commentId);
  void deleteById(Integer id);
  void save(Comment comment);
  void update(Comment comment);
}
