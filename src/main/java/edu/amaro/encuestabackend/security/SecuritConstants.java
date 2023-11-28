package edu.amaro.encuestabackend.security;

import java.util.Base64;

import javax.crypto.SecretKey;
import edu.amaro.encuestabackend.SpringApplicationContext;
import io.jsonwebtoken.security.Keys;

public class SecuritConstants {
    
    public static final long EXPIRATION_DATE = 864000000;
    public static final String LOGIN_URL = "/users/login";
    public static final String  TOKEN_PREFIX = "Baerer ";
    public static final String  HEADER_STRING = "Authorization";

    public static String getTokenSecret(){
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }

    public static SecretKey getKey(){
        if(getTokenSecret().length()*8 < 256){
            System.out.println("clave corta");
            return null;
        }
        byte[] decodedKey = Base64.getDecoder().decode(getTokenSecret());
        SecretKey originalKey = Keys.hmacShaKeyFor(decodedKey);
        return originalKey;
    }
}
