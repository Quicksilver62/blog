package ru.yandex.practicum.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.blog.db.repository.TagRepository;

@Service
@RequiredArgsConstructor
public class TagService {

  private final TagRepository tagRepository;

}
