package ru.yandex.practicum.blog.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostPreviewDao {
  private Integer id;
  private String title;
  private String preview;
  private UUID picture;
  Integer commentsCount;
  Integer likesCount;
  List<TagDao> tags;
  private LocalDateTime createdAt;
}
