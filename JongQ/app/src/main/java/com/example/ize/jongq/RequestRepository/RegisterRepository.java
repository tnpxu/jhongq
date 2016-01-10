package com.example.ize.jongq.RequestRepository;

/**
 * Created by tnpxu on 30/6/2558.
 */
public class RegisterRepository {

    private String username;
    private String password;
    private String nickname;
    private String tel;
    private String gender;

    public RegisterRepository() {

    }

    public RegisterRepository(String username,String password,String nickname,String tel,
                              String gender) {

        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.tel = tel;
        this.gender = gender;

    }


    public String getUsername() {
        return this.username;
    }

    public String getPassword() {

        return this.password;
    }

    public String getNickname() {

        return this.nickname;
    }


    public String getTel() {
        return this.tel;
    }

    public String getGender() {
        return this.gender;
    }

}
