package com.example.springoauth.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.springoauth.core.User;
import com.example.springoauth.dao.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetails, UserDetailsService {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_USERNAME = "username can not be empty";

    private static final String MESSAGE_PASSWORD = "password can not be empty";

    private String username;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    @Autowired
    UserRepository repository;

    public UserDetailsServiceImpl(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsServiceImpl build(User object) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        if (object.getUsername().isEmpty()) {
            throw new RuntimeException(MESSAGE_USERNAME);
        }
        if (object.getPassword().isEmpty()) {
            throw new RuntimeException(MESSAGE_PASSWORD);
        }

        return new UserDetailsServiceImpl(object.getUsername(), object.getPassword(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> objectOptional = repository.findByUsername(username);
        if (objectOptional.isPresent()) {
            throw new RuntimeException("User not found");
        }

        return UserDetailsServiceImpl.build(objectOptional.get());
    }

}
