package com.uexcel.jwt.service.impl;

import com.uexcel.jwt.Mapper.UserMapper;
import com.uexcel.jwt.Repository.UserAuthenticationRepository;
import com.uexcel.jwt.dto.*;
import com.uexcel.jwt.entity.UserAuthentication;
import com.uexcel.jwt.exception.AppExceptions;
import com.uexcel.jwt.service.IUserAuthenticationService;
import com.uexcel.jwt.service.JwtService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IUserAuthenticationServiceImpl implements IUserAuthenticationService {
    private final UserMapper userMapper;
    private final UserAuthenticationRepository uAR;
    private final PasswordEncoder passwordEncoder;
    private final CompromisedPasswordChecker compromisedPasswordChecker;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Logger logger = LoggerFactory.getLogger(IUserAuthenticationServiceImpl.class);
    @Override
    public ResponseDto register(UserAuthenticationDto userAD) {
        if(userAD == null){
            throw new AppExceptions(
                    400,HttpStatus.BAD_REQUEST,"Input UserAuthenticationDto is null.");
        }

        if(compromisedPasswordChecker.check(userAD.getPassword()).isCompromised()){
            throw new AppExceptions(400,HttpStatus.BAD_REQUEST,
                    String.format("Password %s is compromised.",userAD.getPassword()));
        }

        if(uAR.existsByEmailIgnoreCase(userAD.getEmail())){
            throw new AppExceptions(
                    400,HttpStatus.BAD_REQUEST,
                    String.format("The email address %s has been used.",userAD.getEmail()));
        }

        if(userAD.getPassword()!=null && !userAD.getPassword().equals(userAD.getConfirmPassword())){
            throw new AppExceptions(
                    400,HttpStatus.BAD_REQUEST,"Password and ConfirmPassword not matched.");
        }
            userAD.setPassword(passwordEncoder.encode(userAD.getPassword()));
            uAR.save(userMapper.mapToUser(userAD,new UserAuthentication()));

            return new ResponseDto(null,201,HttpStatus.CREATED,
                "User created successfully.",null);
    }

    @Override
    public AccessTokenDto authenticate(LoginDto loginDto) {
            if(loginDto == null){
                throw new AppExceptions(
                        400,HttpStatus.BAD_REQUEST,"Input UserAuthenticationDto is null.");
            }

        UserAuthentication uAT = uAR.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new AppExceptions(
                        400,HttpStatus.BAD_REQUEST,"Bad credentials."));

        if(!passwordEncoder.matches(loginDto.getPassword(),uAT.getPassword())){
            throw new AppExceptions(
                    400,HttpStatus.BAD_REQUEST,"Bad credentials.");
        }

        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        String token = jwtService.generateJwtToken(uAT);

        return new AccessTokenDto(token);
    }

    @Override
    public UserResponseDto fetchUserByEmail(String email) {

        if(email==null || email.isEmpty()){
            UserAuthentication user = (UserAuthentication)
                    SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userMapper.mapToUserDto(user,new UserResponseDto());
        }

        UserAuthentication uA = uAR.findByEmail(email).orElseThrow(() ->
                  new AppExceptions(
                          400,HttpStatus.BAD_REQUEST,
                          "User not found given input data email: "+email)
        );

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();


        if(!uA.getEmail().equals(userEmail) && !uA.getRole().equals("ADMIN")){
            logger.debug("Security breech attempt by username: {}", userEmail);
           throw  new AppExceptions(
                    401,HttpStatus.UNAUTHORIZED,
                    "You are not allowed to access this resource.");
        }

        return userMapper.mapToUserDto(uA,new UserResponseDto());
    }

    /**
     * @return logged in user
     */
    @Override
    public ResponseDto updateUser(UserBaseDto uBD) {
        UserAuthentication user = (UserAuthentication)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setFirstName(uBD.getFirstName());
        user.setLastName(uBD.getLastName());
        uAR.save(user);
        return new ResponseDto(IUserAuthenticationService.getTime(),
                200,HttpStatus.OK,"User updated successfully.","uri=api/update");
    }


}
