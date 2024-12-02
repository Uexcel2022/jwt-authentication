package com.uexcel.jwt.service;

import com.uexcel.jwt.dto.LoginDto;
import com.uexcel.jwt.dto.ResponseDto;
import com.uexcel.jwt.dto.UserAuthenticationDto;
import com.uexcel.jwt.dto.UserTokenDto;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface IUserAuthenticationService {
    ResponseDto register(UserAuthenticationDto userAuthenticationDto);

    UserTokenDto authenticate(LoginDto loginDto);


    static String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
