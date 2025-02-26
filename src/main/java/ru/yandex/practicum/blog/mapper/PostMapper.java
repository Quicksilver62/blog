package ru.yandex.practicum.blog.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.yandex.practicum.blog.dao.PostDao;
import ru.yandex.practicum.blog.dao.PostPreviewDao;
import ru.yandex.practicum.blog.dao.TagDao;
import ru.yandex.practicum.blog.db.model.DbPost;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

  @Mapping(target = "preview", expression = "java(getPreview(dbPost))")
  @Mapping(target = "commentsCount", expression = "java(getCommentCount(dbPost))")
  @Mapping(target = "tags", expression = "java(getTags(dbPost))")
  PostPreviewDao dbToDaoPreview(DbPost dbPost);

  PostDao dbToDao(DbPost dbPost);

  @Mapping(target = "tags", ignore = true)
  DbPost daoToDb(PostDao postDao);

  default String getPreview(DbPost dbPost) {
    String body = dbPost.getBody();
    int maxLength = 3000;
    if (body.length() <= maxLength) {
      return body;
    }
    return body.substring(0, maxLength) + "...";
  }

  default Integer getCommentCount(DbPost dbPost) {
    return dbPost.getComments().size();
  }

  default List<TagDao> getTags(DbPost dbPost) {
    return dbPost.getTags().stream()
        .map(tag -> new TagDao(tag.getId(), tag.getName()))
        .collect(Collectors.toList());
  }
}
