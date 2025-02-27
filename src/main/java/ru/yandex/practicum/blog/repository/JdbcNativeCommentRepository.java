package ru.yandex.practicum.blog.repository;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.blog.domain.Comment;

@Repository
@RequiredArgsConstructor
public class JdbcNativeCommentRepository implements CommentRepository {

  private final JdbcTemplate jdbcTemplate;


  @Override
  public List<Comment> findAllByPostId(int postId) {
    return jdbcTemplate.query(
        """
          select id, post_id, content, author, created_at, updated_at
          from blog.comments where post_id = ?
         """,
        new Object[]{postId},
        (rs, rowNum) -> new Comment(
            rs.getInt("id"),
            rs.getInt("post_id"),
            rs.getString("content"),
            rs.getString("author"),
            rs.getObject("created_at", LocalDateTime.class),
            rs.getObject("updated_at", LocalDateTime.class)
        ));
  }

  @Override
  public void deleteById(Integer id) {
    jdbcTemplate.update(
        "delete from blog.comments where id = ?",
        id
    );
  }

  @Override
  public void save(Comment comment) {
    jdbcTemplate.update(
        "insert into blog.comments (post_id, content, author) values (?, ?, ?)",
        comment.getPostId(),
        comment.getContent(),
        comment.getAuthor()
    );
  }

  @Override
  public void update(Comment comment) {
    jdbcTemplate.update(
        "update blog.comments set post_id = ?, content = ?, author = ? where id = ?",
        comment.getPostId(),
        comment.getContent(),
        comment.getAuthor(),
        comment.getId()
    );
  }
}
