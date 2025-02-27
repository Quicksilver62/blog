package ru.yandex.practicum.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.yandex.practicum.blog.domain.Tag;
import ru.yandex.practicum.blog.dto.TagDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

  TagDto dbToDto(Tag tag);
  Tag dtoToDb(TagDto tagDto);

}
