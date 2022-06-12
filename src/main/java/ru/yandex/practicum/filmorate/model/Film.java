package ru.yandex.practicum.filmorate.model;

import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@Builder
public class Film {

    private Long id;
    @NotBlank
    private final String name;
    @Size(min = 1, max = 200)
    private String description;
    private final LocalDate releaseDate;
    @Positive
    private Integer duration;
    private final Set<Long> likes = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(name, film.name) && Objects.equals(releaseDate, film.releaseDate) && Objects.equals(duration, film.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, releaseDate, duration);
    }
}
