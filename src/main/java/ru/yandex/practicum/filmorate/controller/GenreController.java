package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.Collection;

@RestController
@Slf4j
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public Collection<Genre> findAll() {
        return genreService.findAll();
    }
    @PostMapping
    public Genre create(@RequestBody Genre genre) {
        log.info("Получен запрос к эндпоинту: '{} {}'", "POST", "/genres");
        return genreService.create(genre);
    }
    @PutMapping
    public Genre update(@RequestBody Genre genre) throws StorageException {
        log.info("Получен запрос к эндпоинту: '{} {}'", "PUT", "/genres");
        return genreService.update(genre);
    }
    @DeleteMapping
    public void deleteAllGenre(){
        genreService.deleteAllGenre();
    }

    @GetMapping("/{id}")
    public Genre findGenre(@PathVariable Integer id) throws StorageException {
        return genreService.findGenreById(id);
    }
    @DeleteMapping("/{id}")
    public void deleteGenreById(@PathVariable Integer id){
        genreService.deleteGenreById(id);
    }
}
