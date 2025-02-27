package ru.yandex.practicum.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.yandex.practicum.blog.domain.Post;
import ru.yandex.practicum.blog.dto.PostDto;
import ru.yandex.practicum.blog.dto.PostPreviewDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

  @Mapping(target = "preview", expression = "java(getPreview(dbPost))")
  @Mapping(target = "commentsCount", expression = "java(getCommentCount(dbPost))")
  @Mapping(target = "tags", expression = "java(getTags(dbPost))")
  PostPreviewDto dbToDtoPreview(Post post);

  PostDto dbToDto(Post post);

  Post dtoToDb(PostDto postDto);

  default String getPreview(Post post) {
    String body = post.getContent();
    int maxLength = 3000;
    if (body.length() <= maxLength) {
      return body;
    }
    return body.substring(0, maxLength) + "...";
  }

//  default Integer getCommentCount(DbPost dbPost) {
//    return dbPost.getComments().size();
//  }
//
//  default List<TagDao> getTags(DbPost dbPost) {
//    return dbPost.getTags().stream()
//        .map(tag -> new TagDao(tag.getId(), tag.getName()))
//        .collect(Collectors.toList());
//  }
}
