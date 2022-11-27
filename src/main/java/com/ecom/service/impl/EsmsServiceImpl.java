package com.ecom.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class EsmsServiceImpl {
    @Value("${esms.api.key}")
    private static String apiKey;
    @Value("${esms.secret.key}")
    private static String secretKey;
    @Value("${esms.sms.type}")
    private static String smsType;
    @Value("${esms.brand.name}")
    private static String brandName;

    public static boolean sendMessage(String phoneNumber, String message) {
        String url = "http://rest.esms.vn/MainService.svc/json/SendMultipleMessage_V4_post_json/";
        Map<String, Object> requestMap = new HashMap<>();

        requestMap.put("Phone", phoneNumber);
        requestMap.put("Content", message);
        requestMap.put("ApiKey", apiKey);
        requestMap.put("SecretKey", secretKey);
        requestMap.put("SmsType", smsType);
        requestMap.put("Brandname", brandName);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Object responseObject = restTemplate.postForObject(url, new HttpEntity<>(requestMap, headers), Object.class);

        System.out.println(responseObject);

        return false;
    }

    public static void main(String[] args) {
        apiKey = "50363175741C13121A2DC9A5668C0A";
        secretKey = "D604D50EB31A8211556A583B6AD3E2";
        brandName = "Baotrixemay";
        smsType = "2";

        sendMessage("+84915829646", "058463 la ma xac minh dang ky Baotrixemay cua ban");
    }
}
