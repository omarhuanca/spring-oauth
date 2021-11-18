package com.example.springoauth.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springoauth.core.User;
import com.example.springoauth.dao.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    public List<User> findAllList() {
        return repository.findAll();
    }

    public User save(User obj) {
        return repository.save(obj);
    }

    public User update(User obj) {
        return repository.save(obj);
    }
}
