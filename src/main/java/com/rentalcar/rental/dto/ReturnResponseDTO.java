package com.rentalcar.rental.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ReturnResponseDTO {

    @Schema(description = "ID of the rental", example = "1")
    private Long rentalId;
    @Schema(description = "ID of the car rented", example = "1")
    private Long carId;
    @Schema(description = "Brand of the car rented", example = "BMW 7")
    private String model;
    @Schema(description = "Type of the car rented", example = "PREMIUM")
    private String type;
    @Schema(description = "Start date of the rental", example = "2024-07-12")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate returnDate;
    @Schema(description = "Loyalty points for the car was rented for", example = "5")
    private int loyaltyPoints;
    @Schema(description = "Total points accumulated", example = "10")
    private int totalPoints;
    @Schema(description = "Price of the rental", example = "100.00")
    private BigDecimal priceExtraDay;
}
