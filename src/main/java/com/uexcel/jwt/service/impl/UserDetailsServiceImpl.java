package com.uexcel.jwt.service.impl;

import com.uexcel.jwt.Repository.UserAuthenticationRepository;
import com.uexcel.jwt.entity.UserAuthentication;
import com.uexcel.jwt.exception.AppExceptions;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    final private UserAuthenticationRepository uAR;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthentication uA = uAR.findByEmail(username)
                .orElseThrow(() -> new AppExceptions(
                        400, HttpStatus.BAD_REQUEST,"User Not Found"));
        return uA;
    }
}
