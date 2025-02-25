package ru.yandex.practicum.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.yandex.practicum.blog.dao.PostDao;
import ru.yandex.practicum.blog.dao.PostPreviewDao;
import ru.yandex.practicum.blog.db.model.DbPost;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

  @Mapping(target = "preview", expression = "java(getPreview(dbPost))")
  @Mapping(target = "commentsCount", expression = "java(getCommentCount(dbPost))")
  PostPreviewDao dbToDaoPreview(DbPost dbPost);

  PostDao dbToDao(DbPost dbPost);

  @Mapping(target = "tags", ignore = true)
  DbPost daoToDb(PostDao postDao);

  default String getPreview(final DbPost dbPost) {
    return dbPost.getBody().substring(0, 3000) + "...";
  }

  default Integer getCommentCount(final DbPost dbPost) {
    return dbPost.getComments().size();
  }

}
