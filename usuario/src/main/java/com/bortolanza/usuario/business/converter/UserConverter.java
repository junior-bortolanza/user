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
                .phone(forListPhoneDTO(userDTO.getPhone()))
                .build();
    }

    public List<AddressDTO> forListAddressDTO(List<Address> addressDTO) {
        List<AddressDTO> addresses = new ArrayList<>();
        for (Address address : addressDTO) {
            addresses.add(forAddressDTO(address));
        }
        return addresses;

    }

    public AddressDTO forAddressDTO(Address address) {
        return AddressDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .number(address.getNumber())
                .city(address.getCity())
                .complement(address.getComplement())
                .zipCode(address.getZipCode())
                .state(address.getState())
                .build();
    }

    public List<PhoneDTO> forListPhoneDTO(List<Phone> phoneDTO) {
        return phoneDTO.stream().map(this::forPhoneDTO).toList();
    }

    public PhoneDTO forPhoneDTO(Phone phone) {
        return PhoneDTO.builder()
                .id(phone.getId())
                .number(phone.getNumber())
                .ddd(phone.getDdd())
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

    public Address updateAddress(AddressDTO dto, Address entity) {
        return Address.builder()
                .id(entity.getId())
                .street(dto.getStreet() != null ? dto.getStreet() : entity.getStreet())
                .number(dto.getNumber() != null ? dto.getNumber() : entity.getNumber())
                .city(dto.getCity() != null ? dto.getCity() : entity.getCity())
                .complement(dto.getComplement()!= null ? dto.getComplement() : entity.getComplement())
                .zipCode(dto.getZipCode() != null ? dto.getZipCode() : entity.getZipCode())
                .state(dto.getState()!= null ? dto.getState() : entity.getState())
                .build();
    }

    public Phone updatePhone(PhoneDTO dto, Phone entity) {
        return Phone.builder()
                .id(entity.getId())
                .ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
                .number(dto.getNumber() != null ? dto.getNumber() : entity.getNumber())
                .build();
    }
}
