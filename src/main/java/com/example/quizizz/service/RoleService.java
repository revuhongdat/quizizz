package com.example.quizizz.service;


import com.example.quizizz.model.Role;

public interface RoleService {
    Iterable<Role> findAll();

    void save(Role role);

    Role findByName(String name);
}
