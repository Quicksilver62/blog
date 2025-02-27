package ru.yandex.practicum.blog.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.blog.domain.Tag;
import ru.yandex.practicum.blog.dto.TagDto;
import ru.yandex.practicum.blog.mapper.TagMapper;
import ru.yandex.practicum.blog.repository.TagRepository;

@Service
@RequiredArgsConstructor
public class TagService {

  private final TagRepository tagRepository;
  private final TagMapper tagMapper;

  public List<TagDto> getTagsByPostId(Integer postId) {
    return tagRepository.findAllByPostId(postId)
        .stream()
        .map(tagMapper::dbToDto)
        .collect(Collectors.toList());
  }

  public void deleteTagsByPostId(Integer postId) {
    tagRepository.deleteAllByPostId(postId);
  }

  public void saveTags(List<TagDto> tagDtos) {
    List<Tag> tags = tagDtos.stream()
        .map(tagMapper::dtoToDb)
        .toList();
    tagRepository.saveAll(tags);
  }
}
