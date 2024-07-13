package com.rentalcar.rental.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TypeCarDTO {

        @Schema(description = "ID of the car type", example = "1")
        private Long id;
        @Schema(description = "Name of the car type", example = "SUV")
        private String name;
        @Schema(description = "Price of the car type", example = "100.00")
        private BigDecimal price;
        @Schema(description = "Loyalty points for the car type", example = "5")
        private int loyaltyPoints;
}
