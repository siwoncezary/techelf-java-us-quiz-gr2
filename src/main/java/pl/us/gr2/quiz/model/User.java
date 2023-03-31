package pl.us.gr2.quiz.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

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
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    private String password;

    @Column(unique = true)
    private String email;

    private String roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(roles.split(" ")).map(role -> new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_" + role;
            }
        }).toList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
