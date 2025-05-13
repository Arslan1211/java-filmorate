package ru.yandex.practicum.filmorate.service.mpa;

import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.mapper.MpaMapper;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDbStorage;

@Slf4j
@Service
@RequiredArgsConstructor
public class MpaService {

  private final MpaDbStorage mpaStorage;

  public Collection<MpaDto> getAll() {
    return mpaStorage.getAll()
        .stream()
        .map(MpaMapper::mapToDto)
        .toList();
  }

  public MpaDto getMpa(Short id) {
    Mpa found = mpaStorage.getMpa(id);
    return MpaMapper.mapToDto(found);
  }
}
