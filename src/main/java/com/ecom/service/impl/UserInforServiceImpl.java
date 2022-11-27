package com.ecom.service.impl;

import com.ecom.dto.response.AddressDTO;
import com.ecom.dto.response.PhoneDTO;
import com.ecom.entity.Address;
import com.ecom.entity.PhoneNumber;
import com.ecom.entity.User;
import com.ecom.enumuration.EUserStatus;
import com.ecom.repository.AddressRepository;
import com.ecom.repository.PhoneRepository;
import com.ecom.service.TwilioService;
import com.ecom.service.UserInforService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserInforServiceImpl implements UserInforService {
    private final AddressRepository addressRepository;
    private final PhoneRepository phoneRepository;
    private final ModelMapper modelMapper;
    private final TwilioService twilioService;

    @Override
    public Address createNewAddressForUser(Address address, User user) {
        if (user == null)
            return null;
        address.setUser(user);
        return addressRepository.save(address);
    }

    @Override
    public Boolean deleteAddress(Long idAddress) {
        Address address = addressRepository.findAllById(idAddress);
        if (address == null) return false;
        addressRepository.deleteById(idAddress);
        return true;
    }

    @Override
    public List<AddressDTO> getAllUserAddress(User user) {
        Collection <Address> addresses = user.getAddresses();
        if (addresses == null || addresses.size() == 0)
            return null;
        return addresses.stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PhoneNumber createNewPhoneNumber(PhoneNumber phoneNumber, User user) {
        if (user == null || StringUtils.isBlank(phoneNumber.getPhone()))
            return null;

        PhoneNumber findPhoneNumber = phoneRepository.findByPhoneAndUser_Id(phoneNumber.getPhone(), user.getId());
        if (findPhoneNumber != null)
            return null;

        String verifyCode = RandomStringUtils.randomNumeric(6);
        String message = "Verify code for ecommerce to " + user.getName() + ": " + verifyCode;
        if (!twilioService.sendMessage(phoneNumber.getPhone(), message)) {
            return null;
        }
        phoneNumber.setVerifyCode(verifyCode);
        phoneNumber.setPhoneStatus(EUserStatus.INACTIVE);
        phoneNumber.setUser(user);
        return phoneRepository.save(phoneNumber);
    }

    @Override
    public Boolean verifyPhoneNumber(Long idPhoneNumber, String verifyCode, User user) {
        Boolean res;
        PhoneNumber phoneNumber = phoneRepository.findAllById(idPhoneNumber);
        if (user == null || phoneNumber.getUser().getId() != user.getId())
            return true;
        long expiration = 10 * 60 * 1000;
        long updateMilitime = Timestamp.valueOf(phoneNumber.getUpdatedAt()).getTime();
        if (System.currentTimeMillis() - updateMilitime > expiration) {
            phoneNumber.setVerifyCode(RandomStringUtils.randomAlphanumeric(6));
            res = false;
        } else {
            phoneNumber.setPhoneStatus(EUserStatus.ACTIVE);
            res = true;
        }
        phoneRepository.save(phoneNumber);
        return res;
    }

    @Override
    public Boolean deletePhoneNumber(Long idPhoneNumber) {
        PhoneNumber phoneNumber = phoneRepository.findAllById(idPhoneNumber);
        if (phoneNumber == null)
            return false;
        phoneRepository.deleteById(idPhoneNumber);
        return true;
    }

    @Override
    public List<PhoneDTO> getAllUserPhone(User user) {
        List<PhoneNumber> phoneNumbers = (List<PhoneNumber>) user.getPhoneNumbers();
        if (phoneNumbers == null || phoneNumbers.size() == 0)
            return null;
        return phoneNumbers.stream()
                .map(phoneNumber -> modelMapper.map(phoneNumber, PhoneDTO.class))
                .collect(Collectors.toList());
    }
}
