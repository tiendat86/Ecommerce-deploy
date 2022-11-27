package com.ecom.dto.response;

import com.ecom.enumuration.EUserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDTO {
    private Long id;
    private String phone;
    private EUserStatus status;
}
