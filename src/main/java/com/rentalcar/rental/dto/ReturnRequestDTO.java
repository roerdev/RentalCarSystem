package com.rentalcar.rental.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class ReturnRequestDTO {

    @Schema(description = "ID of the rental to be returned", example = "1")
    @NotNull(message = "Rental ID cannot be null")
    private Long rentalId;
    @Schema(description = "Return date of the car", example = "2024-07-22")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate returnDate;
}
