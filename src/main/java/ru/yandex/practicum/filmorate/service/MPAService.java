package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.StorageException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.MPAStorage;

import java.util.Collection;

@Slf4j
@Service
public class MPAService {
    private final MPAStorage mpaStorage;

    @Autowired
    public MPAService(MPAStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public Collection<MPA> findAll() {
        return mpaStorage.findAll();
    }

    public MPA create(MPA mpa) {
        return mpaStorage.create(mpa);
    }

    public MPA update(MPA mpa) throws StorageException {
        if (findAll().contains(mpa)) {
            return mpaStorage.update(mpa);
        } else {
            throw new StorageException("Данного рейтинга нет в БД");
        }
    }
    public void deleteAllMPA(){
        mpaStorage.deleteAll();
    }
    public MPA findMPAById(Integer id) throws StorageException {
        return mpaStorage.findById(id);
    }
    public boolean deleteMPAById(Integer id) {
        return mpaStorage.delete(id);
    }
}
