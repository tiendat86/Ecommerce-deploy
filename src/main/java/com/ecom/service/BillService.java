package com.ecom.service;

import com.ecom.dto.request.CreateBillRequest;
import com.ecom.entity.Bill;
import com.ecom.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface BillService {
    Bill createBill(CreateBillRequest createBillRequest, User user);
    String paymentBill(HttpServletRequest request, User user, Long idBill, String bankCode, String responseUrl)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException;
    String paymentSuccess(Long idBill);
}
