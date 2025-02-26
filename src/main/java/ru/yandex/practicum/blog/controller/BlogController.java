package ru.yandex.practicum.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.blog.service.PostService;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class BlogController {

  private final PostService postService;
}
