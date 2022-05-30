package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import javax.validation.Valid;
import java.util.Set;


public abstract class Controller <T extends Object> {

    abstract Set<T> findAll();

    abstract T create(@Valid @RequestBody T object) throws ValidationException;


    abstract T update(@Valid @RequestBody T object) throws ValidationException;
}
