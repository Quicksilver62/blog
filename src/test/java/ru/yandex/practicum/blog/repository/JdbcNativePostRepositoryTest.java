package ru.yandex.practicum.blog.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.blog.config.DataSourceConfiguration;
import ru.yandex.practicum.blog.domain.Post;

@SpringJUnitConfig(classes = {DataSourceConfiguration.class, JdbcNativePostRepository.class})
@TestPropertySource(locations = "classpath:application-test.properties")
class JdbcNativePostRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        // Очистка базы данных
        jdbcTemplate.execute("TRUNCATE TABLE blog.posts");
        jdbcTemplate.execute("ALTER TABLE blog.posts ALTER COLUMN id RESTART WITH 1;");

        // Добавление тестовых данных
        jdbcTemplate.execute("INSERT INTO blog.posts (title, content, picture, likes) VALUES ('Title1', 'content1', 'img1', 1)");
        jdbcTemplate.execute("INSERT INTO blog.posts (title, content, picture, likes) VALUES ('Title2', 'content2', 'img2', 2)");
        jdbcTemplate.execute("INSERT INTO blog.posts (title, content, picture, likes) VALUES ('Title3', 'content3', 'img3', 3)");
    }

    @Test
    public void testSaveAndFindById() {
        Post post = new Post(4, "Test Title", "Test Content", "test.jpg", 0, LocalDateTime.now(), LocalDateTime.now());
        postRepository.save(post);

        Optional<Post> foundPost = postRepository.findById(post.getId());
        assertTrue(foundPost.isPresent());
        assertEquals("Test Title", foundPost.get().getTitle());
        assertEquals("Test Content", foundPost.get().getContent());
        assertEquals("test.jpg", foundPost.get().getPicture());
    }

    @Test
    public void testFindAll() {
        List<Post> posts = postRepository.findAll(1, 10);
        assertEquals(3, posts.size());
        assertEquals("Title3", posts.get(0).getTitle()); // Сортировка по дате (DESC)
    }

    @Test
    public void testDeleteById() {
        postRepository.deleteById(1);
        Optional<Post> deletedPost = postRepository.findById(1);
        assertFalse(deletedPost.isPresent());
    }

    @Test
    public void testUpdate() {
        Post post = postRepository.findById(1).orElse(null);
        postRepository.save(post);

        post.setTitle("Updated Title");
        post.setContent("Updated Content");
        postRepository.update(post);

        Optional<Post> updatedPost = postRepository.findById(post.getId());
        assertTrue(updatedPost.isPresent());
        assertEquals("Updated Title", updatedPost.get().getTitle());
        assertEquals("Updated Content", updatedPost.get().getContent());
    }

    @Test
    public void testIncrementLikes() {
        postRepository.incrementLikes(1);
        Optional<Post> updatedPost = postRepository.findById(1);
        assertTrue(updatedPost.isPresent());
        assertEquals(2, updatedPost.get().getLikes());
    }
}
