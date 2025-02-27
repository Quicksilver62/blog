package ru.yandex.practicum.blog.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.blog.config.DataSourceConfiguration;
import ru.yandex.practicum.blog.domain.Comment;

@SpringJUnitConfig(classes = {DataSourceConfiguration.class, JdbcNativeCommentRepository.class})
@TestPropertySource(locations = "classpath:application-test.properties")
public class JdbcNativeCommentRepositoryTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private CommentRepository commentRepository;

  @BeforeEach
  void setUp() {
    // Очистка базы данных
    jdbcTemplate.execute("TRUNCATE TABLE blog.comments");
    jdbcTemplate.execute("ALTER TABLE blog.comments ALTER COLUMN id RESTART WITH 1;");

    // Добавление тестовых данных
    jdbcTemplate.execute("INSERT INTO blog.comments (post_id, content, author) VALUES (1, 'Comment 1', 'Author 1')");
    jdbcTemplate.execute("INSERT INTO blog.comments (post_id, content, author) VALUES (1, 'Comment 2', 'Author 2')");
    jdbcTemplate.execute("INSERT INTO blog.comments (post_id, content, author) VALUES (2, 'Comment 3', 'Author 3')");
  }

  @Test
  public void testFindAllByPostId() {
    // Получаем комментарии для post_id = 1
    List<Comment> comments = commentRepository.findAllByPostId(1);

    // Проверяем, что найдено 2 комментария
    assertEquals(2, comments.size());

    // Проверяем содержимое первого комментария
    Comment firstComment = comments.get(0);
    assertEquals(1, firstComment.getId());
    assertEquals(1, firstComment.getPostId());
    assertEquals("Comment 1", firstComment.getContent());
    assertEquals("Author 1", firstComment.getAuthor());
  }

  @Test
  public void testDeleteById() {
    // Удаляем комментарий с id = 1
    commentRepository.deleteById(1);

    // Проверяем, что комментарий удалён
    List<Comment> comments = commentRepository.findAllByPostId(1);
    assertEquals(1, comments.size()); // Остался только один комментарий
  }

  @Test
  public void testSave() {
    // Создаём новый комментарий
    Comment newComment = new Comment(null, 1, "New Comment", "New Author", LocalDateTime.now(), LocalDateTime.now());
    commentRepository.save(newComment);

    // Проверяем, что комментарий сохранён
    List<Comment> comments = commentRepository.findAllByPostId(1);
    assertEquals(3, comments.size()); // Теперь три комментария для post_id = 1

    // Проверяем содержимое нового комментария
    Comment savedComment = comments.get(2);
    assertEquals("New Comment", savedComment.getContent());
    assertEquals("New Author", savedComment.getAuthor());
  }

  @Test
  public void testUpdate() {
    // Получаем комментарий с id = 1
    List<Comment> comments = commentRepository.findAllByPostId(1);
    Comment commentToUpdate = comments.get(0);

    // Обновляем комментарий
    commentToUpdate.setContent("Updated Content");
    commentToUpdate.setAuthor("Updated Author");
    commentRepository.update(commentToUpdate);

    // Проверяем, что комментарий обновлён
    List<Comment> updatedComments = commentRepository.findAllByPostId(1);
    Comment updatedComment = updatedComments.get(0);
    assertEquals("Updated Content", updatedComment.getContent());
    assertEquals("Updated Author", updatedComment.getAuthor());
  }
}
