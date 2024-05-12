package ru.psuti.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.stereotype.Service;
import ru.psuti.authservice.payload.request.LoginRequest;
import ru.psuti.authservice.payload.response.JwtResponse;
import ru.psuti.authservice.payload.response.LdapResponse;
import ru.psuti.authservice.security.jwt.JwtUtils;
import ru.psuti.authservice.service.AuthService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final LdapTemplate ldapTemplate;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    private static final String BASE_DN = "ou=users,ou=system";


    @Override
    public JwtResponse authenticate(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getCn(), loginRequest.getUserPassword()));


        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(authentication);

        LdapUserDetailsImpl userDetails = (LdapUserDetailsImpl) authentication.getPrincipal();


        System.out.println(userDetails.getAuthorities());
        return ldapTemplate.search(BASE_DN, "(cn=" + loginRequest.getCn() + ")",
                        (AttributesMapper<JwtResponse>) attributes ->
                                new JwtResponse(
                                        jwt,
                                        userDetails.getUsername(),
                                        attributes.get("uid").get().toString(),
                                        attributes.get("sn").get().toString()
                                ))
                .get(0);
    }

    @Override
    public List<LdapResponse> getAllUsers() {
        return ldapTemplate.search(BASE_DN, "(objectclass=inetOrgPerson)",
                    (AttributesMapper<LdapResponse>) attributes ->
                            new LdapResponse(
                                    attributes.get("cn").get().toString(),
                                    attributes.get("sn").get().toString(),
                                    attributes.get("userPassword").get().toString(),
                                    attributes.get("uid").get().toString()
            ));
    }

    @Override
    public LdapResponse getUserByUid(String uid) {
        List<LdapResponse> userDetails = ldapTemplate.search(BASE_DN, "(uid=" + uid + ")",
                (AttributesMapper<LdapResponse>) attributes ->
                        new LdapResponse(
                                attributes.get("cn").get().toString(),
                                attributes.get("sn").get().toString(),
                                attributes.get("userPassword").get().toString(),
                                attributes.get("uid").get().toString()
                        )
        );

        if (!userDetails.isEmpty()) return userDetails.get(0);

        return null;
    }


}
