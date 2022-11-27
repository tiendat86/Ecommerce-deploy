package com.ecom.config;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class VnPayConfig {
    public static final String vnp_Version = "2.1.0";
    public static final String vnp_TmnCode = "EL494B95";
    public static final String vnp_Command = "pay";
    public static final String vnp_HashSecret = "PGEXATIYKIXKGGXODCVJXBVJOFWRTVIW";
    public static final String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static final String vnp_CurrCode = "VND";
    public static final String vnp_Locale = "vi";

    public static String hmacSHA512(final String key, final String data) throws NoSuchAlgorithmException, InvalidKeyException {
        String hmacSHA512Algorithm = "HmacSHA512";
        return hmacWithJava(hmacSHA512Algorithm, data, key);
    }

    private static String hmacWithJava(String algorithm, String data, String key)
            throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(secretKeySpec);
        return bytesToHex(mac.doFinal(data.getBytes()));
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte h : hash) {
            String hex = Integer.toHexString(0xff & h);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
