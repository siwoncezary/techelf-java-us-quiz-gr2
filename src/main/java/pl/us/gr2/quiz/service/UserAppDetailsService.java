package pl.us.gr2.quiz.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.us.gr2.quiz.model.User;
import pl.us.gr2.quiz.repository.UserRepositoryJap;

import java.util.Optional;

@Service
public class UserAppDetailsService implements UserDetailsService {
    private final UserRepositoryJap users;

    public UserAppDetailsService(UserRepositoryJap users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> op = users.findUserByEmail(username);
        if (op.isPresent()){
            return op.get();
        }
        throw new UsernameNotFoundException("User with email " + username +" not found.");
    }
}
