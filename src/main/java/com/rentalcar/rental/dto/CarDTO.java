package com.rentalcar.rental.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
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

    @Schema(description = "ID of the car", example = "1")
    private Long id;
    @Schema(description = "Brand of the car", example = "BMW 7")
    @NotEmpty(message = "Brand is required")
    private String model;
    @Schema(description = "Type of the car")
    private TypeCarDTO type;
    @Schema(description = "Status of the car")
    private StatusCarDTO status;

}
