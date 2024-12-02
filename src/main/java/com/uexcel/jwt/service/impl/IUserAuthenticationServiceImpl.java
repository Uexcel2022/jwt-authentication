package com.uexcel.jwt.service.impl;

import com.uexcel.jwt.Mapper.UserMapper;
import com.uexcel.jwt.Repository.UserAuthenticationRepository;
import com.uexcel.jwt.dto.LoginDto;
import com.uexcel.jwt.dto.ResponseDto;
import com.uexcel.jwt.dto.UserAuthenticationDto;
import com.uexcel.jwt.dto.UserTokenDto;
import com.uexcel.jwt.entity.UserAuthentication;
import com.uexcel.jwt.exception.AppExceptions;
import com.uexcel.jwt.service.IUserAuthenticationService;
import com.uexcel.jwt.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IUserAuthenticationServiceImpl implements IUserAuthenticationService {
    private final UserMapper userMapper;
    private UserAuthenticationRepository uAR;
    private BCryptPasswordEncoder bPE;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public ResponseDto register(UserAuthenticationDto userAD) {
        if(userAD == null){
            throw new AppExceptions(
                    400,HttpStatus.BAD_REQUEST,"Input UserAuthenticationDto is null");
        }
    if(userAD.getPassword()!=null && !userAD.getPassword().equals(userAD.getConfirmPassword())){
        throw new AppExceptions(
                400,HttpStatus.BAD_REQUEST,"Password and ConfirmPassword not matched");
    }
        userAD.setPassword(bPE.encode(userAD.getPassword()));
        uAR.save(userMapper.mapToUser(userAD,new UserAuthentication()));

        return new ResponseDto(null,201,HttpStatus.CREATED,
                "You have registered successfully.",null);
    }

    @Override
    public UserTokenDto authenticate(LoginDto loginDto) {
        if(loginDto == null){
            if(loginDto == null){
                throw new AppExceptions(
                        400,HttpStatus.BAD_REQUEST,"Input UserAuthenticationDto is null");
            }
        }

        UserAuthentication uAT = uAR.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new AppExceptions(
                        400,HttpStatus.BAD_REQUEST,"Bad credentials"));

        if(!bPE.matches(loginDto.getPassword(),uAT.getPassword())){
            throw new AppExceptions(
                    400,HttpStatus.BAD_REQUEST,"Bad credentials");
        }

        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        String token = jwtService.generateJwtToken(uAT);

        return new UserTokenDto(token);
    }
}
