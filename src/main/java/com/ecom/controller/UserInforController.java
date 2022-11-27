package com.ecom.controller;

import com.ecom.dto.response.AddressDTO;
import com.ecom.entity.Address;
import com.ecom.entity.PhoneNumber;
import com.ecom.service.UserInforService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserInforController extends BaseController {
    private final UserInforService userInforService;

    @PostMapping("/address/create")
    public Address createPhoneNumber(@RequestBody Address address) {
        return userInforService.createNewAddressForUser(address, getUser());
    }

    @GetMapping("/address/delete/{idAddress}")
    public Boolean deleteAddress(@PathVariable Long idAddress) {
        return userInforService.deleteAddress(idAddress);
    }

    @GetMapping("/address/all_address")
    public List<AddressDTO> getAllAddress() {
        return userInforService.getAllUserAddress(getUser());
    }

    @PostMapping("/phone/create")
    public PhoneNumber createPhoneNumber(@RequestBody PhoneNumber phoneNumber) {
        return userInforService.createNewPhoneNumber(phoneNumber, getUser());
    }

    @GetMapping("/phone/verify")
    public Boolean verifyPhoneNumber(@PathParam("id") Long idPhone, @PathParam("code") String verifyCode) {
        return userInforService.verifyPhoneNumber(idPhone, verifyCode, getUser());
    }

    @GetMapping("/phone/delete/{idPhone}")
    public Boolean deletePhoneNumber(@PathVariable Long idPhone) {
        return userInforService.deletePhoneNumber(idPhone);
    }
}
