package ru.yandex.practicum.blog.service;

import java.awt.print.Pageable;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.blog.dao.PostDao;
import ru.yandex.practicum.blog.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  public List<PostDao> getAllPosts(Pageable pageable) {
    return Collections.EMPTY_LIST;
  }

  public PostDao getPostById(Integer id) {
    return new PostDao();
  }

  public void addPost(PostDao post) {

  }

  public void updatePost(PostDao post) {

  }

  public void deletePost(Integer postId) {
    postRepository.deleteById(postId);
  }
}
