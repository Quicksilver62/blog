package ru.yandex.practicum.blog.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
  private Integer id;
  private String title;
  private String content;
  private String preview;
  private String picture;
  List<CommentDto> comments;
  Integer likesCount;
  List<TagDto> tags;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
