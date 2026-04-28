package com.bortolanza.usuario.business.converter;

import com.bortolanza.usuario.business.dto.AddressDTO;
import com.bortolanza.usuario.business.dto.PhoneDTO;
import com.bortolanza.usuario.business.dto.UserDTO;
import com.bortolanza.usuario.infrastructure.entity.Address;
import com.bortolanza.usuario.infrastructure.entity.Phone;
import com.bortolanza.usuario.infrastructure.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter {

    public UserDTO forUserDTO(User userDTO) {
        return UserDTO.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .address(forListAddressDTO(userDTO.getAddress()))
                .build();
    }

    public List<AddressDTO> forListAddressDTO(List<Address> addressDTO) {
        List<AddressDTO> addresses = new ArrayList<>();
        for (Address address : addressDTO) {
            addresses.add(forAddress(address));
        }
        return addresses;

    }

    public AddressDTO forAddress(Address addressDTO) {
        return AddressDTO.builder()
                .street(addressDTO.getStreet())
                .number(addressDTO.getNumber())
                .city(addressDTO.getCity())
                .complement(addressDTO.getComplement())
                .zipCode(addressDTO.getZipCode())
                .state(addressDTO.getState())
                .build();
    }

    public List<PhoneDTO> forListPhoneDTO(List<Phone> phoneDTO) {
        return phoneDTO.stream().map(this::forPhone).toList();
    }

    public PhoneDTO forPhone (Phone phoneDTO) {
        return PhoneDTO.builder()
                .number(phoneDTO.getNumber())
                .ddd(phoneDTO.getDdd())
                .build();
    }
    public User forUser(UserDTO userDTO) {
        return User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .address(forListAddress(userDTO.getAddress()))
                .phone(forListPhone(userDTO.getPhone()))
                .build();
    }

    public List<Address> forListAddress(List<AddressDTO> addressDTO) {
        return addressDTO.stream().map(this::forAddress).toList();

    }

    public Address forAddress(AddressDTO addressDTO) {
        return Address.builder()
                .street(addressDTO.getStreet())
                .number(addressDTO.getNumber())
                .city(addressDTO.getCity())
                .complement(addressDTO.getComplement())
                .zipCode(addressDTO.getZipCode())
                .state(addressDTO.getState())
                .build();
    }

    public List<Phone> forListPhone(List<PhoneDTO> phoneDTO) {
        return phoneDTO.stream().map(this::forPhone).toList();
    }

    public Phone forPhone (PhoneDTO phoneDTO) {
        return Phone.builder()
                .number(phoneDTO.getNumber())
                .ddd(phoneDTO.getDdd())
                .build();
    }

    public User updateUser(UserDTO userDTO, User entity) {
        return User.builder()
                .name(userDTO.getName() != null ? userDTO.getName() : entity.getName())
                .id(entity.getId())
                .password(userDTO.getPassword() != null ? userDTO.getPassword() : entity.getPassword())
                .email(userDTO.getEmail() != null ? userDTO.getEmail() : entity.getEmail())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .build();
    }
}
