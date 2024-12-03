package com.uexcel.jwt.service;

import com.uexcel.jwt.dto.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface IUserAuthenticationService {

    ResponseDto register(UserAuthenticationDto userAuthenticationDto);

    AccessTokenDto authenticate(LoginDto loginDto);

    UserResponseDto fetchUserByEmail(String email);

    ResponseDto updateUser(UserBaseDto baseDto);


    static String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
