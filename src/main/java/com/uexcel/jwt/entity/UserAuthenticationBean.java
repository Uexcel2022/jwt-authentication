package com.uexcel.jwt.entity;

import jakarta.persistence.EntityListeners;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public class UserAuthenticationBean {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
