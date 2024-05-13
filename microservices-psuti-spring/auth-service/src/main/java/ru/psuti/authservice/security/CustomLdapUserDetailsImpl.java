package ru.psuti.authservice.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import ru.psuti.authservice.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class CustomLdapUserDetailsImpl extends LdapUserDetailsImpl implements LdapUserDetails {

    private static final String BASE_DN = "ou=users,ou=system";
    private String uid;
    private String cn;
    private String sn;
    private String password;
    private Collection<GrantedAuthority> authorities;

    public static CustomLdapUserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new CustomLdapUserDetailsImpl(
                user.getUid(),
                user.getCn(),
                user.getSn(),
                user.getUserPassword(),
                authorities);
    }


    @Override
    public String getDn() {
        return BASE_DN;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return uid;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomLdapUserDetailsImpl that = (CustomLdapUserDetailsImpl) o;
        return Objects.equals(uid, that.uid) && Objects.equals(cn, that.cn) && Objects.equals(sn, that.sn) && Objects.equals(password, that.password);
    }
}

