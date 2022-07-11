package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.LikeService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;
    private final LikeService likeService;
    @Autowired
    public FilmController(FilmService filmService, LikeService likeService) {
        this.filmService = filmService;
        this.likeService = likeService;
    }

    @GetMapping
    public Collection<Film> findAll() {
        return filmService.findAll();
    }
    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        log.info("Получен запрос к эндпоинту: '{} {}', Название фильма: '{}'", "POST", "/films", film.getName());
        return filmService.create(film);
    }
     @PutMapping
    public Film update(@Valid @RequestBody Film film) throws ValidationException, StorageException {
        log.info("Получен запрос к эндпоинту: '{} {}', Название фильма: '{}'", "PUT", "/films", film.getName());
        return filmService.update(film);
    }
    @DeleteMapping
    public void deleteAllFilms(){
        filmService.deleteAllFilms();
    }
   @GetMapping ("/{id}")
   public Film findFilm(@PathVariable long id) throws StorageException {
           return filmService.findFilmById(id);
       }
    @DeleteMapping ("/{id}")
    public boolean deleteFilm(@PathVariable long id) {
        return filmService.deleteFilmById(id);
    }

    //PUT /films/{id}/like/{userId}
    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable long id, @PathVariable long userId) {
        likeService.addLike(id, userId);
    }
    //DELETE /films/{id}/like/{userId}
    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable long id, @PathVariable long userId) throws StorageException {
        likeService.deleteLike(id, userId);
    }
    //GET /films/popular?count={count}
    @GetMapping("/popular")
    public Collection<Film> printTopFilms(@RequestParam(defaultValue = "10") int count){
        return likeService.printTopFilms(count);
    }

}

