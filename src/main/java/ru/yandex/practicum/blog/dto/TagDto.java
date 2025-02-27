package ru.yandex.practicum.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {
  private Integer id;
  private Integer postId;
  private String name;
}
