package com.ecom.service;

import com.ecom.dto.response.AddressDTO;
import com.ecom.dto.response.PhoneDTO;
import com.ecom.entity.Address;
import com.ecom.entity.PhoneNumber;
import com.ecom.entity.User;

import java.util.List;

public interface UserInforService {
    Address createNewAddressForUser(Address address, User user);
    Boolean deleteAddress(Long idAddress);
    List<AddressDTO> getAllUserAddress(User user);
    PhoneNumber createNewPhoneNumber(PhoneNumber phoneNumber, User user);
    Boolean verifyPhoneNumber(Long idPhoneNumber, String verifyCode, User user);
    Boolean deletePhoneNumber(Long idPhoneNumber);
    List<PhoneDTO> getAllUserPhone(User user);
}
