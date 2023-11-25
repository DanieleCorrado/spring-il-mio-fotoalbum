package com.example.backend.security;

import com.example.backend.model.Role;
import com.example.backend.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DatabaseUserDetails implements UserDetails {

    // Campi specifici per un DatabaseUserDetails
    private Integer id;
    private String username;
    private String password;
    private Set<GrantedAuthority> authorities = new HashSet<>();

    // Costruttore che copia da una istanza di User i dati che mi servono
    public DatabaseUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getEmail();
        this.password = user.getPassword();
        // per ogni ruolo creo una GrantedAuthority
        for (Role role : user.getRoles()) {
            this.authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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

    public Integer getId() {
        return id;
    }
}
