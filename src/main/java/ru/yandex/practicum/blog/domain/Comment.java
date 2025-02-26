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
public class Comment {
  private Integer id;
  private Post post;
  private String text;
  private String author;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
