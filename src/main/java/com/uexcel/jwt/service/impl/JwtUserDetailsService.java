package com.uexcel.jwt.service.impl;

import com.uexcel.jwt.Repository.UserAuthenticationRepository;
import com.uexcel.jwt.model.UserAuthentication;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {
    final private UserAuthenticationRepository uAR;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthentication uA = uAR.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("Bad credentials")
                );
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(uA.getRole()));
        return new User(uA.getEmail(),uA.getPassword(),authorities);
    }
}
