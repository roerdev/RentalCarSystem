package com.rentalcar.rental.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @NotNull(message = "Client ID cannot be null")
    private Long clientId;
    @NotNull(message = "Car ID cannot be null")
    private Long carId;
    @NotNull(message = "Rental days cannot be null")
    private Integer days;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate currencyDate;
}
