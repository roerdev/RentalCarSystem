package com.rentalcar.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {

    private Long id;
    private String model;
    private TypeCarDTO type;

}
