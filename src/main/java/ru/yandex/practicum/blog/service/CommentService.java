package ru.yandex.practicum.blog.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.blog.domain.Comment;
import ru.yandex.practicum.blog.dto.CommentDto;
import ru.yandex.practicum.blog.mapper.CommentMapper;
import ru.yandex.practicum.blog.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final CommentMapper commentMapper;

  public void createOrUpdateComment(CommentDto commentDto) {
    Comment comment;
    if (commentDto.getId() != null) {
      comment = commentRepository.findById(commentDto.getId()).orElseThrow();
      comment.setContent(commentDto.getContent());
      commentRepository.update(comment);
    }
    else {
      comment = commentMapper.dtoToDb(commentDto);
      commentRepository.save(comment);
    }
  }

  public void deleteComment(Integer id) {
    commentRepository.deleteById(id);
  }

  public List<CommentDto> getCommentsByPostId(Integer postId) {
    return commentRepository.findAllByPostId(postId)
        .stream()
        .map(commentMapper::dbToDto)
        .collect(Collectors.toList());
  }
}
