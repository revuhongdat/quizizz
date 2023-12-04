package com.example.quizizz.service;

import com.example.quizizz.model.Role;
import com.example.quizizz.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JwtSpring3Application implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Autowired
    public JwtSpring3Application(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(JwtSpring3Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            Role admin = new Role("ADMIN");
            Role teacher = new Role("TEACHER");
            Role student = new Role("STUDENT");
            roleRepository.save(admin);
            roleRepository.save(teacher);
            roleRepository.save(student);
        }
    }
}

