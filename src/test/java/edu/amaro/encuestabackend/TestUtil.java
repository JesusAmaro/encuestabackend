package edu.amaro.encuestabackend;

import java.util.Random;

import edu.amaro.encuestabackend.models.request.UserRegisterRequestModel;

public class TestUtil {
    
    public static  UserRegisterRequestModel createValidUser(){
        UserRegisterRequestModel user = new UserRegisterRequestModel();
        user.setEmail(generateRandomString(16) + "@gmail.com");
        user.setName(generateRandomString(8));
        user.setPassword(generateRandomString(8));;
        return user;
    }

    public static String generateRandomString(int len){
        String charrs = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk1mnopqrstuvwxyz";

        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);

        for(int i = 0; i < len; i++){
            sb.append(charrs.charAt(rnd.nextInt(charrs.length())));
        }

        return sb.toString();
    }
}
