package ru.yandex.practicum.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.yandex.practicum.blog.dao.TagDao;
import ru.yandex.practicum.blog.db.model.DbTag;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {
  TagDao dbToDao(DbTag dbTag);
  DbTag daoToDb(TagDao tagDao);
}
