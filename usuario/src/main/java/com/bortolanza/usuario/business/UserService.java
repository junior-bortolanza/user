package com.bortolanza.usuario.business;

import com.bortolanza.usuario.business.converter.UserConverter;
import com.bortolanza.usuario.business.dto.UserDTO;
import com.bortolanza.usuario.infrastructure.entity.User;
import com.bortolanza.usuario.infrastructure.exceptions.ConflictException;
import com.bortolanza.usuario.infrastructure.exceptions.ResourceNotFoundException;
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

    public User searchUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email not found!"));
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
}
