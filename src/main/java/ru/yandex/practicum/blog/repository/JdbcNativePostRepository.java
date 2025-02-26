package ru.yandex.practicum.blog.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.blog.domain.Post;

@Repository
@RequiredArgsConstructor
public class JdbcNativePostRepository implements PostRepository {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public List<Post> findAll(int pageNumber, int pageSize) {
    int offset = (pageNumber - 1) * pageSize;
    return jdbcTemplate.query(
        """
          select id, title, body, picture, likes, created_at, updated_at 
          from posts order by created_at desc 
          offset ? limit ?
         """,
        new Object[]{offset, pageSize},
        (rs, rowNum) -> new Post(
            rs.getInt("id"),
            rs.getString("title"),
            rs.getString("body"),
            rs.getString("picture"),
            rs.getInt("likes"),
            rs.getObject("created_at", LocalDateTime.class),
            rs.getObject("updated_at", LocalDateTime.class)
        ));
  }

  @Override
  public Optional<Post> findById(Integer postId) {
    return jdbcTemplate.query(
        "select id, title, body, picture, likes, created_at, updated_at from posts where id = ?",
        new Object[]{postId},
        (rs, rowNum) -> new Post(
            rs.getInt("id"),
            rs.getString("title"),
            rs.getString("body"),
            rs.getString("picture"),
            rs.getInt("likes"),
            rs.getObject("created_at", LocalDateTime.class),
            rs.getObject("updated_at", LocalDateTime.class)
        )
    ).stream().findFirst();
  }

  @Override
  public void deleteById(Integer id) {
    jdbcTemplate.update(
        "delete from posts where id = ?",
        id
    );
  }

  @Override
  public void save(Post post) {
    jdbcTemplate.update(
        "insert into posts (title, body, picture, likes) values (?, ?, ?, ?)",
        post.getTitle(),
        post.getBody(),
        post.getPicture(),
        0
    );
  }

  @Override
  public void incrementLikes(Integer postId) {
    jdbcTemplate.update(
        "update posts set likes = (select likes where post_id = ?) + 1 where post id = ?",
        postId
    );
  }

}
