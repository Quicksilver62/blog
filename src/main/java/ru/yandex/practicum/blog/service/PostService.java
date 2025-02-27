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
import ru.yandex.practicum.blog.dto.PostDto;
import ru.yandex.practicum.blog.dto.PostPreviewDto;
import ru.yandex.practicum.blog.mapper.PostMapper;
import ru.yandex.practicum.blog.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostService {

  private static final String UPLOAD_DIR = "$TOMCAT_HOME/webapps/static/";

  private final PostRepository postRepository;
  private final PostMapper postMapper;
  private final CommentService commentService;
  private final TagService tagService;

  public Slice<PostPreviewDto> getAllPosts(Pageable pageable) {

    var postDtos = postRepository.findAll(pageable.getPageNumber(), pageable.getPageSize())
        .stream()
        .map(postMapper::dbToDtoPreview)
        .peek(post -> {
          var comments = commentService.getCommentsByPostId(post.getId());
          var tags = tagService.getTagsByPostId(post.getId());
          post.setCommentsCount(comments.size());
          post.setTags(tags);
        })
        .toList();

    return new SliceImpl<>(postDtos);
  }

  public PostDto getPostById(Integer id) {
    var post = postRepository.findById(id)
        .map(postMapper::dbToDto)
        .orElse(null);
    if (post != null) {
      post.setComments(commentService.getCommentsByPostId(id));
      post.setTags(tagService.getTagsByPostId(id));
    }
    return post;
  }

  public void createOrUpdatePost(PostDto postDto, MultipartFile file) {
    var fileUrl = savePicture(file);
    postDto.setPicture(fileUrl);
    if (postDto.getId() == null) {
      addPost(postDto);
    }
    else {
      updatePost(postDto);
    }
  }

  public void deletePost(Integer postId) {
    postRepository.deleteById(postId);
  }

  private void addPost(PostDto postDto) {
    tagService.saveTags(postDto.getTags());
    postRepository.save(postMapper.dtoToDb(postDto));
  }

  private void updatePost(PostDto postDto) {
    tagService.deleteTagsByPostId(postDto.getId());
    tagService.saveTags(postDto.getTags());
    postRepository.update(postMapper.dtoToDb(postDto));
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
