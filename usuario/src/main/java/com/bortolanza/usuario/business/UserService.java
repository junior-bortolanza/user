package com.bortolanza.usuario.business;

import com.bortolanza.usuario.business.converter.UserConverter;
import com.bortolanza.usuario.business.dto.UserDTO;
import com.bortolanza.usuario.infrastructure.entity.User;
import com.bortolanza.usuario.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserDTO saveUser(UserDTO userDTO) {
        User user = userConverter.forUser(userDTO);
        user = userRepository.save(user);
        return userConverter.forUser(user);
    }
}
