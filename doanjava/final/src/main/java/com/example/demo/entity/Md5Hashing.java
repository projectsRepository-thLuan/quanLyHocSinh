package com.example.demo.entity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Hashing {
    public static String Encrypt(String pw){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pw.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < bytes.length; i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return  generatedPassword;
    }
}
