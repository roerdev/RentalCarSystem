package com.rentalcar.rental.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

    @Schema(description = "ID of the client renting the car", example = "1")
    @NotNull(message = "Client ID cannot be null")
    private Long clientId;
    @Schema(description = "ID of the car to be rented", example = "1")
    @NotNull(message = "Car ID cannot be null")
    private Long carId;
    @Schema(description = "Number of days the car is rented for", example = "10")
    @Min(value = 1, message = "Rental days must be at least 1")
    @NotNull(message = "Rental days cannot be null")
    private Integer days;
    @Schema(description = "Current Date", example = "2024-07-12")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate currencyDate;
}
