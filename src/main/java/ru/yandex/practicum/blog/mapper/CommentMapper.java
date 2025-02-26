package ru.yandex.practicum.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.yandex.practicum.blog.dao.CommentDao;
import ru.yandex.practicum.blog.db.model.DbComment;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
  CommentDao dbToDao(DbComment dbComment);
  DbComment daoToDb(CommentDao commentDao);
}
