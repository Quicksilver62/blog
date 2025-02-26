package ru.yandex.practicum.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.blog.dao.CommentDao;
import ru.yandex.practicum.blog.db.model.DbComment;
import ru.yandex.practicum.blog.db.repository.CommentRepository;
import ru.yandex.practicum.blog.mapper.CommentMapper;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final CommentMapper commentMapper;

  public void createOrUpdateComment(CommentDao commentDao) {
    DbComment dbComment;
    if (commentDao.getId() != null) {
      dbComment = commentRepository.findById(commentDao.getId()).orElseThrow();
      dbComment.setText(commentDao.getText());
    }
    else {
      dbComment = commentMapper.daoToDb(commentDao);
    }
    commentRepository.save(dbComment);
  }

  public void deleteComment(Integer id) {
    commentRepository.deleteById(id);
  }
}
