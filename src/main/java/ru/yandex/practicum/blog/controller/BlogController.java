package ru.yandex.practicum.blog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogController {

  @GetMapping("/home") // Принимаем GET-запрос по адресу /home
  @ResponseBody        // Указываем, что возвращаемое значение является ответом
  public String homePage() {
    return "<h1>Hello, world!</h1>"; // Ответ
  }

}
