package com.team4.caps.config;

import jdk.jfr.Unsigned;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;



@Configuration
public class SecurityConfig {


    public static String encoder(String password)
    {
        if(password==null)password="123456";
        long hashCode=47L;
        StringBuilder passwordBuilder = new StringBuilder(password);
        long before=47;
        for(long i = (long) passwordBuilder.length(); i<=128L; i++)
        {
            before=(i*before+114514L)%997;
            //before=Math.max(0,before);
            passwordBuilder.append((char)(before % 7+41));
        }
        password = passwordBuilder.toString();
        //System.out.println(password);
        StringBuilder encodeResult=new StringBuilder();
        for(int i=0;i<128;i++)
        {
            long c=((password.charAt(i)*hashCode)%128)%93+32;
            encodeResult.append((char)(c+1));
        }
        return encodeResult.toString();
    }

}
