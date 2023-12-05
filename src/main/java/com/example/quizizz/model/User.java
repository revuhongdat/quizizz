package com.example.quizizz.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String confirmPassword;

    @CreationTimestamp
    private LocalDateTime timeCreate;

    @UpdateTimestamp
    private LocalDateTime lastTimeVisit;

    private String image;

    private int status;

    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "id_user")},
            inverseJoinColumns = {@JoinColumn(name = "id_role")})
    private Set<Role> roles = new HashSet<>();

    public User(String username, String password, String confirmPassword, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.roles = roles;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public LocalDateTime getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(LocalDateTime timeCreate) {
        this.timeCreate = timeCreate;
    }

    public LocalDateTime getLastTimeVisit() {
        return lastTimeVisit;
    }

    public void setLastTimeVisit(LocalDateTime lastTimeVisit) {
        this.lastTimeVisit = lastTimeVisit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
