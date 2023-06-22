package com.team4.caps.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;



@Configuration
public class SecurityConfig {

    public static Pbkdf2PasswordEncoder encoder = Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    public static String encoder(String password)
    {
        return  encoder.encode(password);
    }

}
