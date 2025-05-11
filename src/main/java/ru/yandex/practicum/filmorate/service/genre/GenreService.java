package ru.yandex.practicum.filmorate.service.genre;

import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;

@Service
@RequiredArgsConstructor
public class GenreService {

  private final GenreDbStorage genreStorage;

  public Collection<GenreDto> getAll() {
    return genreStorage.getAll()
        .stream()
        .map(GenreMapper::mapToGenreDto)
        .toList();
  }

  public GenreDto getGenre(Short id) {
    Genre found = genreStorage.getGenre(id);
    return GenreMapper.mapToGenreDto(found);
  }
}