package ru.yandex.practicum.blog.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

  private Integer id;
  private String title;
  private String body;
  private String picture;
  private Integer likes;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}
