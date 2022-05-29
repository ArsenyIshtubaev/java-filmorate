package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String email;
    private String login;
    private String name;
    private final LocalDate birthday;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
