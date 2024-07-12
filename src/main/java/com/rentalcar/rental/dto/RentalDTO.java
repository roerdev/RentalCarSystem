package com.rentalcar.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalDTO {

    private Long id;
    private CarDTO car;
    private ClientDTO client;
    private LocalDate startDate;
    private LocalDate endDate;
    private int rentalDays;
    private BigDecimal price;
}
