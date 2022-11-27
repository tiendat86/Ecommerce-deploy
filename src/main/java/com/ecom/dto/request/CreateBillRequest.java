package com.ecom.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateBillRequest {
    private List<Long> listIdProductInCart;
}
