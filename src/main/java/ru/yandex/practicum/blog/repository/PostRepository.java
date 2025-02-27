package ru.yandex.practicum.blog.repository;

import java.util.List;
import java.util.Optional;
import ru.yandex.practicum.blog.domain.Post;

public interface PostRepository {

  List<Post> findAll(int pageNumber, int pageSize);
  Optional<Post> findById(Integer postId);
  void deleteById(Integer id);
  void save(Post post);
  void update(Post post);
  void incrementLikes(Integer postId);
}
