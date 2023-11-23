package com.example.backend.security;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // prendo lo username che viene dalla login e cerco su db uno user con quella email
        Optional<User> loggedUser = userRepository.findByEmail(username);
        if (loggedUser.isPresent()) {
            // c'è un utente con quella email
            // restituisco un DatabaseUserDetails con i dati dello User
            return new DatabaseUserDetails(loggedUser.get());
        } else {
            // non c'è un utente con quella email
            throw new UsernameNotFoundException(username);
        }
    }
}
