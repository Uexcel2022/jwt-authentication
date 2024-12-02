package com.uexcel.jwt.service;

import com.uexcel.jwt.dto.ResponseDto;
import com.uexcel.jwt.dto.UserAuthenticationDto;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface IUserAuthenticationService {
    ResponseDto register(UserAuthenticationDto userAuthenticationDto);



    static String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
