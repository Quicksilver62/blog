package ru.yandex.practicum.blog.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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

  private static final String UPLOAD_DIR = "$TOMCAT_HOME/webapps/static/";

  private final PostRepository postRepository;
  private final TagRepository tagRepository;
  private final PostMapper postMapper;
  private final TagMapper tagMapper;

  public Slice<PostPreviewDao> getAllPosts(Pageable pageable) {
    var dbPosts = postRepository.findAll(pageable);

    var daoList = dbPosts.getContent()
        .stream()
        .map(postMapper::dbToDaoPreview)
        .toList();

     return new SliceImpl<>(daoList, dbPosts.getPageable(), dbPosts.hasNext());
  }

  public PostDao getPostById(Integer id) {
    return postRepository.findById(id)
        .map(postMapper::dbToDao)
        .orElse(null);
  }

  public void createOrUpdatePost(PostDao post, MultipartFile file) {
    DbPost dbPost;
    var fileUrl = savePicture(file);
    post.setPicture(fileUrl);

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

  public void deletePost(Integer id) {
    postRepository.deleteById(id);
  }

  public void likePost(Integer id) {
    var dbPost = postRepository.findById(id).orElseThrow();
    dbPost.setLikesCount(dbPost.getLikesCount() + 1);
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

  private String savePicture(MultipartFile picture) {
    if (picture.isEmpty()) {
      return null;
    }

    try {
      Path uploadPath = Paths.get(UPLOAD_DIR);
      if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
      }

      String pictureName = UUID.randomUUID() + "_" + picture.getOriginalFilename();

      Path filePath = uploadPath.resolve(pictureName);
      Files.copy(picture.getInputStream(), filePath);

      return "/static/" + pictureName;
    } catch (IOException e) {
      throw new RuntimeException("Failed to save picture", e);
    }
  }
}
