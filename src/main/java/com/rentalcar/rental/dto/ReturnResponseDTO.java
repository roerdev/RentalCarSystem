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
public class ReturnResponseDTO {

    private Long rentalId;
    private Long carId;
    private String model;
    private String type;
    private LocalDate returnDate;
    private int loyaltyPoints;
    private int totalPoints;
    private BigDecimal priceExtraDay;
}
