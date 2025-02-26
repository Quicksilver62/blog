package ru.yandex.practicum.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.blog.dao.CommentDao;
import ru.yandex.practicum.blog.service.CommentService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts/comment")
public class CommentController {

  CommentService commentService;

  @PostMapping
  public String createOrUpdateComment(@ModelAttribute CommentDao comment) {
    commentService.createOrUpdateComment(comment);
    return "redirect:/posts";
  }

  @DeleteMapping("/delete/{id}")
  public String deleteComment(@PathVariable Integer id) {
    commentService.deleteComment(id);
    return "redirect:/posts";
  }
}
