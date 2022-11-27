package com.ecom.service;

public interface TwilioService {
    boolean sendMessage(String phoneNumber, String message);
}
