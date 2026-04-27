package com.bortolanza.usuario.business.dto;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneDTO {

    private String number;
    private String ddd;


}
