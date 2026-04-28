package com.bortolanza.usuario.controller;

import com.bortolanza.usuario.business.UserService;
import com.bortolanza.usuario.business.dto.AddressDTO;
import com.bortolanza.usuario.business.dto.PhoneDTO;
import com.bortolanza.usuario.business.dto.UserDTO;
import com.bortolanza.usuario.infrastructure.entity.User;
import com.bortolanza.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.saveUser(userDTO));
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getEmail(),
                        userDTO.getPassword())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<UserDTO> searchUserByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(userService.searchUserByEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUserByEmail(@PathVariable("email") String email) {
        userService.deleteUserByEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO dto,
                                              @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.updateUserData(token, dto));
    }

    @PutMapping("/endereco")
    public ResponseEntity<AddressDTO> updateAddress(@RequestBody AddressDTO dto,
                                                    @RequestParam("id") Long id) {
        return ResponseEntity.ok(userService.updateAddress(id, dto));
    }

    @PutMapping("/telefone")
    public ResponseEntity<PhoneDTO> updateAddress(@RequestBody PhoneDTO dto,
                                                  @RequestParam("id") Long id) {
        return ResponseEntity.ok(userService.updatePhone(id, dto));
    }
}

