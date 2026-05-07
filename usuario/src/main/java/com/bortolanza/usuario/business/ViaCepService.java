package com.bortolanza.usuario.business;

import com.bortolanza.usuario.infrastructure.client.ViaCepClient;
import com.bortolanza.usuario.infrastructure.client.ViaCepDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ViaCepService {

    private final ViaCepClient client;

    public ViaCepDTO searchDataAddresses(String cep) {
        try {
            return client.searchDataAddresses(processCep(cep));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro:", e);
        }
    }

    private String processCep(String cep) {
        String cepFormat = cep.replace(" ", "")
                .replace("-", "");


        if (!cepFormat.matches("\\d+") || !Objects.equals(cepFormat.length(), 8)) {
            throw new IllegalArgumentException("O cep contém caracteres inválidos, favor verificar!");
        }

        return cepFormat;
    }
}
