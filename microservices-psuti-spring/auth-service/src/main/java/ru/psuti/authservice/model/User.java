package ru.psuti.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String dn;

    private String uid;

    private String cn;

    private String sn;

    private String userPassword;

    private Set<Role> roles = new HashSet<>();

    public User(String uid, String cn, String sn, String userPassword) {


        this.uid = uid;
        this.cn = cn;
        this.sn = sn;
        this.userPassword = userPassword;
    }
}
