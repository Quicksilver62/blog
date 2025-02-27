package ru.yandex.practicum.blog.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
  private Integer id;
  private String content;
  private String author;
  private LocalDateTime createdAt;
}
