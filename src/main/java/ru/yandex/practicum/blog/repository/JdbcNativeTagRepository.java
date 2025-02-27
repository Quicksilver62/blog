package ru.yandex.practicum.blog.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.blog.domain.Tag;

@Repository
@RequiredArgsConstructor
public class JdbcNativeTagRepository implements TagRepository {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public List<Tag> findAllByPostId(int postId) {
    return jdbcTemplate.query(
        """
          select id, post_id, name, created_at, updated_at
          from blog.tags where post_id = ?
         """,
        new Object[]{postId},
        (rs, rowNum) -> new Tag(
            rs.getInt("id"),
            rs.getInt("post_id"),
            rs.getString("name"),
            rs.getObject("created_at", LocalDateTime.class),
            rs.getObject("updated_at", LocalDateTime.class)
        ));
  }

  @Override
  public void deleteAllByPostId(int postId) {
    jdbcTemplate.update(
        "delete from blog.tags where post_id = ?",
        postId
    );
  }

  @Override
  public void saveAll(List<Tag> tags) {
    jdbcTemplate.batchUpdate("insert into blog.tags (post_id, name) values (?, ?)",
        new BatchPreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        Tag tag = tags.get(i);
        ps.setInt(1, tag.getPostId());
        ps.setString(2, tag.getName());
      }

      @Override
      public int getBatchSize() {
        return tags.size();
      }
    });
  }
}
