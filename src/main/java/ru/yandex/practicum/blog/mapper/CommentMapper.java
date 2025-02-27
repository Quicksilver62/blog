package ru.yandex.practicum.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.yandex.practicum.blog.dto.CommentDto;
import ru.yandex.practicum.blog.domain.Comment;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

  CommentDto dbToDto(Comment comment);
  Comment dtoToDb(CommentDto commentDto);
}
