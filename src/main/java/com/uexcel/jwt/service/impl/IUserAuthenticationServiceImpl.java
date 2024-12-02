package com.uexcel.jwt.service.impl;

import com.uexcel.jwt.Mapper.UserMapper;
import com.uexcel.jwt.dto.ResponseDto;
import com.uexcel.jwt.dto.UserAuthenticationDto;
import com.uexcel.jwt.exception.AppExceptions;
import com.uexcel.jwt.service.IUserAuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IUserAuthenticationServiceImpl implements IUserAuthenticationService {
    private final UserMapper userMapper;
    @Override
    public ResponseDto register(UserAuthenticationDto userAD) {
        if(userAD != null){
            throw new AppExceptions(
                    400,HttpStatus.BAD_REQUEST,"Input UserAuthenticationDto is null");
        }
        return null;
    }
}
