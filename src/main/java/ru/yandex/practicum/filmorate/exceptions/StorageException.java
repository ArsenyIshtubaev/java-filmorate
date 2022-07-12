package ru.yandex.practicum.filmorate.exceptions;

import java.util.function.Supplier;

public class StorageException extends Exception {
    public StorageException(String message) {
        super(message);
    }

}
