package ru.yandex.practicum.blog.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.blog.config.DataSourceConfiguration;
import ru.yandex.practicum.blog.domain.Tag;

@SpringJUnitConfig(classes = {DataSourceConfiguration.class, JdbcNativeTagRepository.class})
@TestPropertySource(locations = "classpath:application-test.properties")
public class JdbcNativeTagRepositoryTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private TagRepository tagRepository;

  @BeforeEach
  void setUp() {
    // Очистка базы данных
    jdbcTemplate.execute("TRUNCATE TABLE blog.tags");
    jdbcTemplate.execute("ALTER TABLE blog.tags ALTER COLUMN id RESTART WITH 1;");

    // Добавление тестовых данных
    jdbcTemplate.execute("INSERT INTO blog.tags (post_id, name) VALUES (1, 'Tag 1')");
    jdbcTemplate.execute("INSERT INTO blog.tags (post_id, name) VALUES (1, 'Tag 2')");
    jdbcTemplate.execute("INSERT INTO blog.tags (post_id, name) VALUES (2, 'Tag 3')");
  }

  @Test
  public void testFindAllByPostId() {
    // Получаем теги для post_id = 1
    List<Tag> tags = tagRepository.findAllByPostId(1);

    // Проверяем, что найдено 2 тега
    assertEquals(2, tags.size());

    // Проверяем содержимое первого тега
    Tag firstTag = tags.get(0);
    assertEquals(1, firstTag.getId());
    assertEquals(1, firstTag.getPostId());
    assertEquals("Tag 1", firstTag.getName());
  }

  @Test
  public void testDeleteAllByPostId() {
    // Удаляем все теги для post_id = 1
    tagRepository.deleteAllByPostId(1);

    // Проверяем, что теги удалены
    List<Tag> tags = tagRepository.findAllByPostId(1);
    assertTrue(tags.isEmpty()); // Список тегов пуст
  }

  @Test
  public void testSaveAll() {
    // Создаём список новых тегов
    List<Tag> newTags = List.of(
        new Tag(4, 1, "NewTag1", LocalDateTime.now(), LocalDateTime.now()),
        new Tag(5, 1, "NewTag2", LocalDateTime.now(), LocalDateTime.now())
    );

    // Сохраняем теги
    tagRepository.saveAll(newTags);

    // Проверяем, что теги сохранены
    List<Tag> tags = tagRepository.findAllByPostId(1);
    assertEquals(4, tags.size()); // Теперь 4 тега для post_id = 1

    // Проверяем содержимое новых тегов
    Tag savedTag1 = tags.get(2);
    assertEquals("NewTag1", savedTag1.getName());

    Tag savedTag2 = tags.get(3);
    assertEquals("NewTag2", savedTag2.getName());
  }
}
