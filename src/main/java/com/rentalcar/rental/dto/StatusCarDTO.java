package com.rentalcar.rental.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
public class StatusCarDTO {

    @Schema(description = "ID of the status", example = "1")
    @NotNull
    private Long id;
    @Schema(description = "Status of the car", example = "RENTED")
    private String status;
}
