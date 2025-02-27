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
public class Tag {
  private Integer id;
  private Integer postId;
  private String name;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
