package com.ecom.controller;

import com.ecom.dto.request.CreateBillRequest;
import com.ecom.entity.Bill;
import com.ecom.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
public class BillController extends BaseController {
    private final BillService billService;

    @PostMapping("/user/bill/create")
    public Bill createBill(@RequestBody CreateBillRequest request) {
        return billService.createBill(request, getUser());
    }

    @GetMapping("/user/pay_bill")
    public String paymentBill(HttpServletRequest request, @RequestParam("id") Long idBill,
                              @RequestParam("code") String bankCode) {
        String responseUrl = getStringUrl(request) + "/user/bill_success/" + idBill;
        try {
            return billService.paymentBill(request, getUser(), idBill,
                    bankCode, responseUrl);
        } catch (Exception e) {
            return "payment_fail";
        }
    }

    private String getStringUrl(HttpServletRequest request) {
        String siteUrl = request.getRequestURL().toString();
        return siteUrl.replace(request.getServletPath(), "");
    }

    @GetMapping("/user/bill_success/{id}")
    public String responsePayment(@PathVariable("id") Long idBill) {
        return billService.paymentSuccess(idBill);
    }
}
