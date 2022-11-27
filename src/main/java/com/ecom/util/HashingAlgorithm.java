package com.ecom.util;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class HashingAlgorithm {
    public String hashingAlorithm(String str, String algorithm) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            byte[] hasing = messageDigest.digest(str.getBytes());
            return convertToHex(hasing);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    private String convertToHex(byte[] hasing) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < hasing.length; i++) {
            stringBuffer.append(Integer.toString((hasing[i] & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuffer.toString();
    }
}
