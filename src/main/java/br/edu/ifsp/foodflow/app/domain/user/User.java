package br.edu.ifsp.foodflow.app.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class User {
    private UUID id;
    private String name;
    private String username;
    private String email;
    private String password;

    public User(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
