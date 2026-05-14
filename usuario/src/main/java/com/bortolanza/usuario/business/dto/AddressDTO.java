package com.bortolanza.usuario.business.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {

    private Long id;
    private String street;
    private Long number;
    private String complement;
    private String city;
    private String state;
    private String zipCode;

}
