package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Builder
public class MPA {
    private Integer id;
    private String name;

    public MPA(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MPA mpa = (MPA) o;
        return Objects.equals(id, mpa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
