package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.MPAService;

import java.util.Collection;

@RestController
@Slf4j
@RequestMapping("/mpa")
public class MPAController {
    private final MPAService mpaService;

    @Autowired
    public MPAController(MPAService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping
    public Collection<MPA> findAll() {
        log.info("Получен запрос к эндпоинту: '{} {}'", "GET", "/mpa");
        return mpaService.findAll();
    }
    @PostMapping
    public MPA create(@RequestBody MPA mpa) {
        log.info("Получен запрос к эндпоинту: '{} {}'", "POST", "/mpa");
        return mpaService.create(mpa);
    }
    @PutMapping
    public MPA update(@RequestBody MPA mpa) throws StorageException {
        log.info("Получен запрос к эндпоинту: '{} {}'", "PUT", "/mpa");
        return mpaService.update(mpa);
    }
    @DeleteMapping
    public void deleteAllMPA(){
        log.info("Получен запрос к эндпоинту: '{} {}'", "DELETE", "/mpa");
        mpaService.deleteAllMPA();
    }

    @GetMapping("/{id}")
    public MPA findMPA(@PathVariable Integer id) throws StorageException {
        log.info("Получен запрос к эндпоинту: '{} {}'", "GET", "/mpa/{id}");
        return mpaService.findMPAById(id);
    }
    @DeleteMapping("/{id}")
    public void deleteMPAById(@PathVariable Integer id){
        log.info("Получен запрос к эндпоинту: '{} {}'", "DELETE", "/mpa/{id}");
        mpaService.deleteMPAById(id);
    }
}
