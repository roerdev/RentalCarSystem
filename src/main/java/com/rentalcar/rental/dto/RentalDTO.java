package com.rentalcar.rental.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "ID of the rental", example = "1")
    private Long id;
    @Schema(description = "Car rented")
    private CarDTO car;
    @Schema(description = "Client renting the car")
    private ClientDTO client;
    @Schema(description = "Start date of the rental", example = "2024-07-12")
    private LocalDate startDate;
    @Schema(description = "End date of the rental", example = "2024-07-22")
    private LocalDate endDate;
    @Schema(description = "Number of days the car is rented for", example = "10")
    private int rentalDays;
    @Schema(description = "Price of the rental", example = "100.00")
    private BigDecimal price;
}
