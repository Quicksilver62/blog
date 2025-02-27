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
public class PostDao {
  private Integer id;
  private String title;
  private String content;
  private String preview;
  private String picture;
  List<CommentDao> comments;
  Integer likesCount;
  List<TagDao> tags;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
