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
          select id, title, content, picture, likes, created_at, updated_at 
          from blog.posts order by created_at desc 
          limit ? offset ?
         """,
        new Object[]{pageSize, offset},
        (rs, rowNum) -> new Post(
            rs.getInt("id"),
            rs.getString("title"),
            rs.getString("content"),
            rs.getString("picture"),
            rs.getInt("likes"),
            rs.getObject("created_at", LocalDateTime.class),
            rs.getObject("updated_at", LocalDateTime.class)
        ));
  }

  @Override
  public Optional<Post> findById(Integer postId) {
    return jdbcTemplate.query(
        """
        select id, title, content, picture, likes, created_at, updated_at 
        from blog.posts where id = ?""",
        new Object[]{postId},
        (rs, rowNum) -> new Post(
            rs.getInt("id"),
            rs.getString("title"),
            rs.getString("content"),
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
        "delete from blog.posts where id = ?",
        id
    );
  }

  @Override
  public void save(Post post) {
    jdbcTemplate.update(
        "insert into blog.posts (title, content, picture, likes) values (?, ?, ?, ?)",
        post.getTitle(),
        post.getContent(),
        post.getPicture(),
        0
    );
  }

  @Override
  public void update(Post post) {
    jdbcTemplate.update(
        "update blog.posts set title = ?, content = ?, picture = ?, likes = ? where id = ?",
        post.getTitle(),
        post.getContent(),
        post.getPicture(),
        post.getLikes(),
        post.getId()
    );
  }

  @Override
  public void incrementLikes(Integer postId) {
    jdbcTemplate.update(
        "update blog.posts set likes = likes + 1 where id = ?",
        postId
    );
  }

}
