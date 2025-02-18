package ru.yandex.practicum.blog.dao;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDao {
  private Integer id;
  private String text;
  private String author;
  private LocalDateTime createdAt;
}
