package pl.us.gr2.quiz.model;

import jakarta.persistence.*;
import lombok.*;

// uzupełnij klasę User  o pola:
// - email
// - password
// - id
// dodaj adnotacje, aby klasa była encją
// dodaj także adnotacje lombok'a
// dodaj interfejs repozytorium dla klasy User
// wstrzyknij repozytorium klasy User do klasy QuizApplication
// wstaw do bazy dwóch użytkowników w metodzie run
@Entity(name = "app_users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    private String password;

    private String email;
}
