package com.rentalcar.rental.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class ClientDTO {

    @Schema(description = "ID of the client", example = "1")
    private Long id;
    @Schema(description = "Name of the client", example = "John Doe")
    private String name;
    @Schema(description = "Total points accumulated", example = "10")
    private int loyaltyPoints;
}
