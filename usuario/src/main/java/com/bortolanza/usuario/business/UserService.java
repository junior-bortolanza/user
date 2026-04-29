package com.bortolanza.usuario.business;

import com.bortolanza.usuario.business.converter.UserConverter;
import com.bortolanza.usuario.business.dto.AddressDTO;
import com.bortolanza.usuario.business.dto.PhoneDTO;
import com.bortolanza.usuario.business.dto.UserDTO;
import com.bortolanza.usuario.infrastructure.entity.Address;
import com.bortolanza.usuario.infrastructure.entity.Phone;
import com.bortolanza.usuario.infrastructure.entity.User;
import com.bortolanza.usuario.infrastructure.exceptions.ConflictException;
import com.bortolanza.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.bortolanza.usuario.infrastructure.repository.AddressRepository;
import com.bortolanza.usuario.infrastructure.repository.PhoneRepository;
import com.bortolanza.usuario.infrastructure.repository.UserRepository;
import com.bortolanza.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AddressRepository addressRepository;
    private final PhoneRepository phoneRepository;

    public UserDTO saveUser(UserDTO userDTO) {
        emailExists(userDTO.getEmail());
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userConverter.forUser(userDTO);
        user = userRepository.save(user);
        return userConverter.forUserDTO(user);
    }

    public void emailExists(String email){
        try {
            boolean exist = userRepository.existsByEmail(email);
            if(exist){
                throw new ConflictException("Email already exists");
            }
        } catch (ConflictException e){
            throw new ConflictException("Email already exists" + e.getCause());
        }
    }

    public boolean verifyExistingEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserDTO searchUserByEmail(String email) {
        try{
            return userConverter.forUserDTO(userRepository.findByEmail(email)
                .orElseThrow(() ->
                new ResourceNotFoundException("Email not found!")));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email not found!");
        }
    }

    public void deleteUserByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    public UserDTO updateUserData(String token, UserDTO dto) {
        //Aqui buscamos o email do usuario atraves do token (tirar a obrigatoriedade do email)
        String email = jwtUtil.extractEmailToken(token.substring(7));

        //Criptografia de senha
        dto.setPassword(dto.getPassword() != null ? passwordEncoder.encode(dto.getPassword()) : null);

        //Busca os dados do usuário no banco de dados
        User userEntity = userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email not found!"));

        //Mesclou os dados que recebemos na requisiÇão DTO com os dados do banco de dados
        User user = userConverter.updateUser(dto, userEntity);

        // Salvou os dados do usuário convertido e depois pegou o retorno e converteu para UsuarioDTO
        return userConverter.forUserDTO(userRepository.save(user));
    }

    public AddressDTO updateAddress(Long idAddress, AddressDTO dto) {
        Address entity = addressRepository.findById(idAddress).orElseThrow(() ->
                new ResourceNotFoundException("Address not found!" + idAddress));

        Address address = userConverter.updateAddress(dto, entity);

        return userConverter.forAddressDTO(addressRepository.save(address));
    }

    public PhoneDTO updatePhone(Long idPhone, PhoneDTO dto) {
        Phone entity = phoneRepository.findById(idPhone).orElseThrow(() ->
                new ResourceNotFoundException("Phone not found!" + idPhone));

        Phone phone = userConverter.updatePhone(dto, entity);

        return userConverter.forPhoneDTO(phoneRepository.save(phone));
    }

    public AddressDTO registerAddress(String token, AddressDTO dto) {
        String email = jwtUtil.extractEmailToken(token.substring(7));
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email not found!"));
        Address address = userConverter.forAddressToEntity(dto, user.getId());
        Address addressEntity = addressRepository.save(address);
        return userConverter.forAddressDTO(addressRepository.save(addressEntity));
    }

    public PhoneDTO registerPhone(String token, PhoneDTO dto) {
        String email = jwtUtil.extractEmailToken(token.substring(7));
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email not found!"));

        Phone phone = userConverter.forPhoneToEntity(dto, user.getId());
        Phone phoneEntity = phoneRepository.save(phone);
        return userConverter.forPhoneDTO(phoneRepository.save(phoneEntity));
    }
}
