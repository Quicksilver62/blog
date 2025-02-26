package ru.yandex.practicum.blog.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.yandex.practicum.blog.configuration.WebConfiguration;
import ru.yandex.practicum.blog.dao.PostDao;
import ru.yandex.practicum.blog.dao.PostPreviewDao;
import ru.yandex.practicum.blog.service.PostService;

@SpringJUnitConfig(classes = {WebConfiguration.class})
@WebAppConfiguration
@TestPropertySource(locations = "classpath:application-test.properties")
public class PostControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Mock
  private PostService postService;

  @InjectMocks
  private PostController postController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
  }

  @Test
  void testGetAllPosts() throws Exception {
    Slice<PostPreviewDao> posts = new PageImpl<>(Collections.singletonList(new PostPreviewDao()));
    when(postService.getAllPosts(any(Pageable.class))).thenReturn(posts);

    mockMvc.perform(get("/posts")
            .param("page", "0")
            .param("size", "10"))
        .andExpect(status().isOk())
        .andExpect(view().name("posts/list"))
        .andExpect(model().attributeExists("posts"))
        .andExpect(model().attributeExists("post"));

    verify(postService, times(1)).getAllPosts(any(Pageable.class));
  }

  @Test
  void testGetPostById() throws Exception {
    PostDao post = new PostDao();
    post.setId(1);
    when(postService.getPostById(1)).thenReturn(post);

    mockMvc.perform(get("/posts/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("posts/detail"))
        .andExpect(model().attributeExists("post"))
        .andExpect(model().attribute("post", post));

    verify(postService, times(1)).getPostById(1);
  }

  @Test
  void testLikePost() throws Exception {
    mockMvc.perform(get("/posts/1/like"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("posts/detail"));

    verify(postService, times(1)).likePost(1);
  }

  @Test
  void testCreateOrUpdatePost() throws Exception {
    PostDao post = new PostDao();
    post.setId(1);
    post.setTitle("Test Post");

    mockMvc.perform(post("/posts")
            .contentType("application/json")
            .content("{\"id\":1,\"title\":\"Test Post\"}"))
        .andExpect(status().isOk())
        .andExpect(content().string("Post created/updated successfully"));

    verify(postService, times(1)).createOrUpdatePost(any(PostDao.class), isNull());
  }

  @Test
  void testDeletePost() throws Exception {
    mockMvc.perform(delete("/posts/delete/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/posts"));

    verify(postService, times(1)).deletePost(1);
  }
}
