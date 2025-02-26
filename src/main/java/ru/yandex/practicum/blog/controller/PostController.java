package ru.yandex.practicum.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.blog.dao.PostDao;
import ru.yandex.practicum.blog.dao.PostPreviewDao;
import ru.yandex.practicum.blog.service.PostService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

  private final PostService postService;

  @GetMapping
  public String getAllPosts(@PageableDefault(size = 10) Pageable pageable, Model model) {
    Slice<PostPreviewDao> posts = postService.getAllPosts(pageable);
    model.addAttribute("posts", posts);
    model.addAttribute("post", new PostPreviewDao());
    return "posts/list";
  }

  @GetMapping("/{id}")
  public String getPostById(@PathVariable Integer id, Model model) {
    PostDao post = postService.getPostById(id);
    model.addAttribute("post", post);
    return "posts/detail";
  }

  @GetMapping("/{id}/like")
  public String getPostById(@PathVariable Integer id) {
    postService.likePost(id);
    return "posts/detail";
  }

  @PostMapping
  @ResponseBody
  public String createOrUpdatePost(@RequestBody PostDao post,
      @RequestParam("picture") MultipartFile file) {
    postService.createOrUpdatePost(post, file);
    return "Post created/updated successfully";
  }

  @DeleteMapping("/delete/{id}")
  public String deletePost(@PathVariable Integer id) {
    postService.deletePost(id);
    return "redirect:/posts";
  }
}
