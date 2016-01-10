package com.example.ize.jongq.RequestRepository;

/**
 * Created by tnpxu on 30/6/2558.
 */
public class LoginRepository {

    private String username;
    private String password;

    public LoginRepository() {

    }

    public LoginRepository(String username, String password) {

        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}
