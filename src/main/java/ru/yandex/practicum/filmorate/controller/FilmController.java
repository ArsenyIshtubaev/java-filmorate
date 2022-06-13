package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.IsInStorageException;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> findAll() {
        return filmService.findAll();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ValidationException, IsInStorageException {
        log.info("Получен запрос к эндпоинту: '{} {}', Название фильма: '{}'", "POST", "/films", film.getName());
        filmService.validate(film);
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) throws ValidationException, StorageException {
        log.info("Получен запрос к эндпоинту: '{} {}', Название фильма: '{}'", "PUT", "/films", film.getName());
        filmService.validate(film);
        return filmService.update(film);
    }
   // GET .../users/{id}
   @GetMapping ("/{id}")
   public Film findFilm(@PathVariable Long id) throws StorageException {
           return filmService.findFilmById(id);
       }
    //PUT /films/{id}/like/{userId}
    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId) throws StorageException {
        filmService.addLike(id, userId);
    }
    //DELETE /films/{id}/like/{userId}
    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Long id, @PathVariable Long userId) throws StorageException {
        filmService.removeLike(id, userId);
    }
    //GET /films/popular?count={count}
    @GetMapping("/popular")
    public List<Film> printTopFilms(@RequestParam(defaultValue = "10") int count){
        return filmService.printTopFilms(count);
    }

}

