package com.uexcel.jwt.Mapper;

import com.uexcel.jwt.dto.UserAuthenticationDto;
import com.uexcel.jwt.dto.UserResponseDto;
import com.uexcel.jwt.model.UserAuthentication;
import com.uexcel.jwt.role.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {
    private final Role role;
    public UserAuthentication mapToUser
            (UserAuthenticationDto uAD, UserAuthentication uA){
        uA.setFirstName(uAD.getFirstName());
        uA.setLastName(uAD.getLastName());
        uA.setPassword(uAD.getPassword());
        uA.setEmail(uAD.getEmail());
        uA.setRole(role.user());
        return uA;

    }

    public UserResponseDto mapToUserDto(
            UserAuthentication uA, UserResponseDto uRD){
        uRD.setId(uA.getId());
        uRD.setFirstName(uA.getFirstName());
        uRD.setLastName(uA.getLastName());
        uRD.setEmail(uA.getEmail());
        uRD.setRole(uA.getRole());
        return uRD;
    }


}
