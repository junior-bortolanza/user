package com.bortolanza.usuario.controller;

import com.bortolanza.usuario.business.UserService;
import com.bortolanza.usuario.business.ViaCepService;
import com.bortolanza.usuario.business.dto.AddressDTO;
import com.bortolanza.usuario.business.dto.PhoneDTO;
import com.bortolanza.usuario.business.dto.UserDTO;
import com.bortolanza.usuario.infrastructure.client.ViaCepDTO;
import com.bortolanza.usuario.infrastructure.exceptions.UnauthorizedException;
import com.bortolanza.usuario.infrastructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Tag(name = "Usuário", description = "Cadastro e login e usuários")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class UserController {

    private final UserService userService;
    private final ViaCepService viaCepService;


    @PostMapping
    @Operation(summary = "Salvar Usuários", description = "Cria um novo usuário!")
    @ApiResponse(responseCode = "200", description = "Usuário salvo com sucesso!")
    @ApiResponse(responseCode = "400", description = "Usuário já cadastrado!")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.saveUser(userDTO));
    }

    @PostMapping("/login")
    @Operation(summary = "Login Usuários", description = "Cria um novo usuário!")
    @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso!")
    @ApiResponse(responseCode = "401", description = "Credencias inválidas")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) throws UnauthorizedException {
        return ResponseEntity.ok(userService.authenticateUser(userDTO));
    }

    @GetMapping
    @Operation(summary = "Buscar dados de Usuários por Email"
            , description = "Buscar dados do usuário!")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado!")
    @ApiResponse(responseCode = "403", description = "Usuário não cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    @ApiResponse(responseCode = "401", description = "Credencias inválidas")
    public ResponseEntity<UserDTO> searchUserByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(userService.searchUserByEmail(email));
    }

    @DeleteMapping("/{email}")
    @Operation(summary = "Deleta Usuário por Id", description = "Deleta usuário!")
    @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso!")
    @ApiResponse(responseCode = "403", description = "Usuário não cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    @ApiResponse(responseCode = "401", description = "Credencias inválidas")
    public ResponseEntity<Void> deleteUserByEmail(@PathVariable("email") String email) {
        userService.deleteUserByEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Operation(summary = "Atualizar Dados de Usuário", description = "Atualizar dados de usuário!")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso!")
    @ApiResponse(responseCode = "403", description = "Usuário não cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    @ApiResponse(responseCode = "401", description = "Credencias inválidas")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO dto,
                                              @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.updateUserData(token, dto));
    }

    @PutMapping("/endereco")
    @Operation(summary = "Atualizar EndereÇo de Usuários", description = "Atualiza endereÇo de usuário!")
    @ApiResponse(responseCode = "200", description = "EndereÇo atualizado com sucesso!")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    @ApiResponse(responseCode = "401", description = "Credencias inválidas")
    public ResponseEntity<AddressDTO> updateAddress(@RequestBody AddressDTO dto,
                                                    @RequestParam("id") Long id) {
        return ResponseEntity.ok(userService.updateAddress(id, dto));
    }

    @PutMapping("/telefone")
    @Operation(summary = "Atualizar Telefone de Usuários", description = "Atualiza telefone de usuário!")
    @ApiResponse(responseCode = "200", description = "telefone atualizado com sucesso!")
    @ApiResponse(responseCode = "403", description = "Usuário já cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    @ApiResponse(responseCode = "401", description = "Credencias inválidas")
    public ResponseEntity<PhoneDTO> updateAddress(@RequestBody PhoneDTO dto,
                                                  @RequestParam("id") Long id) {
        return ResponseEntity.ok(userService.updatePhone(id, dto));
    }

    @PostMapping("/endereco")
    @Operation(summary = "Salva EndereÇo de Usuários", description = "Salva endereÇo de usuário!")
    @ApiResponse(responseCode = "200", description = "EndereÇo salvo com sucesso!")
    @ApiResponse(responseCode = "403", description = "Endereço não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    @ApiResponse(responseCode = "401", description = "Credencias inválidas")
    public ResponseEntity<AddressDTO> registerAddress(@RequestBody AddressDTO dto,
                                                      @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.registerAddress(token, dto));
    }

    @PostMapping("/telefone")
    @Operation(summary = "Salva Telfone de Usuários", description = "Salva telefone de usuário!")
    @ApiResponse(responseCode = "200", description = "Telefone salvo com sucesso!")
    @ApiResponse(responseCode = "403", description = "Telefone não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    @ApiResponse(responseCode = "401", description = "Credencias inválidas")
    public ResponseEntity<PhoneDTO> registerPhone(@RequestBody PhoneDTO dto,
                                                  @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.registerPhone(token, dto));
    }

    @GetMapping("/endereco/{cep}")
    public ResponseEntity<ViaCepDTO> searchDataCep(@PathVariable("cep") String cep) {
        return ResponseEntity.ok(viaCepService.searchDataAddresses(cep));
    }
}

