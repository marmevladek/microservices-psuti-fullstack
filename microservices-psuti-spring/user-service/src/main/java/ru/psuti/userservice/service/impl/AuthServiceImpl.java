package ru.psuti.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.stereotype.Service;

import ru.psuti.userservice.payload.request.LoginRequest;
import ru.psuti.userservice.payload.response.AuthResponse;
import ru.psuti.userservice.security.jwt.JwtUtils;
import ru.psuti.userservice.service.AuthService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final LdapTemplate ldapTemplate;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    private static final String BASE_DN = "ou=users,ou=system";

    @Override
    public AuthResponse authenticate(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getCn(), loginRequest.getUserPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateToken(authentication);

            LdapUserDetailsImpl userDetails = (LdapUserDetailsImpl) authentication.getPrincipal();

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            return ldapTemplate.search(BASE_DN, "(cn=" + loginRequest.getCn() + ")",
                    (AttributesMapper<AuthResponse>) attr ->
                        new AuthResponse(
                                attr.get("uid").get().toString(),
                                roles,
                                jwt
                        ))
                    .get(0);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }
}
