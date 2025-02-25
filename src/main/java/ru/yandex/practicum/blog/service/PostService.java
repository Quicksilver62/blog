package ru.yandex.practicum.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.blog.dao.PostDao;
import ru.yandex.practicum.blog.dao.PostPreviewDao;
import ru.yandex.practicum.blog.db.model.DbPost;
import ru.yandex.practicum.blog.db.repository.PostRepository;
import ru.yandex.practicum.blog.db.repository.TagRepository;
import ru.yandex.practicum.blog.mapper.PostMapper;
import ru.yandex.practicum.blog.mapper.TagMapper;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;
  private final TagRepository tagRepository;
  private final PostMapper postMapper;
  private final TagMapper tagMapper;

  public Slice<PostPreviewDao> getAllPosts(Pageable pageable) {
    var dbSlice = postRepository.findAll(pageable);

    var daoList = dbSlice.getContent()
        .stream()
        .map(postMapper::dbToDaoPreview)
        .peek(post -> {
          var tags = tagRepository.findAllByPostId(post.getId())
              .stream()
              .map(tagMapper::dbToDao)
              .toList();
          post.setTags(tags);
        })
        .toList();

     return new SliceImpl<>(daoList, dbSlice.getPageable(), dbSlice.hasNext());
  }

  public PostDao getPostById(Integer id) {
    return postRepository.findById(id)
        .map(postMapper::dbToDao)
        .orElse(null);
  }

  public void addAndUpdatePost(PostDao post) {
    DbPost dbPost;
    if (post.getId() != null) {
      dbPost = postRepository.findById(post.getId()).orElseThrow();
      updatePost(dbPost, post);
    }
    else {
      dbPost = postMapper.daoToDb(post);
      setTags(dbPost, post);
    }
    postRepository.save(dbPost);
  }

  public void deletePost(Integer postId) {
    postRepository.deleteById(postId);
  }

  private void updatePost(DbPost dbPost, PostDao postDao) {
    tagRepository.deleteAllByPostId(dbPost.getId());
    dbPost.setTitle(postDao.getTitle());
    dbPost.setBody(postDao.getBody());
    dbPost.setPicture(postDao.getPicture());
    setTags(dbPost, postDao);
  }

  private void setTags(DbPost dbPost, PostDao postDao) {
    var dbTags = postDao.getTags()
        .stream()
        .map(tagMapper::daoToDb)
        .toList();
    dbPost.setTags(dbTags);
  }
}
