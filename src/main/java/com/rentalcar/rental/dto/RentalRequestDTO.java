package com.rentalcar.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalRequestDTO {

    private Long clientId;
    private Long carId;
    private Integer days;
    private LocalDate currencyDate;
}
