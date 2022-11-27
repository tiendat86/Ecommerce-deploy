package com.ecom.service.impl;

import com.ecom.service.TwilioService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioServiceImpl implements TwilioService {
    @Value("${twilio.accound.sid}")
    private String accoundSid;
    @Value("${twilio.auth.token}")
    private String authToken;
    @Value("${twilio.messaging.service.sid}")
    private String messasingServiceSid;

    @Override
    public boolean sendMessage(String phoneNumber, String message) {
        try {
            Twilio.init(accoundSid, authToken);
            Message.creator(new PhoneNumber(phoneNumber), messasingServiceSid, message)
                    .create();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
