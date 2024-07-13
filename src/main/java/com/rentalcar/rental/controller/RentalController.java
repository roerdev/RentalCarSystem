package com.rentalcar.rental.controller;

import com.rentalcar.rental.dto.RentalDTO;
import com.rentalcar.rental.dto.RentalRequestDTO;
import com.rentalcar.rental.dto.ReturnRequestDTO;
import com.rentalcar.rental.dto.ReturnResponseDTO;
import com.rentalcar.rental.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rentals")
@RequiredArgsConstructor
@Tag(name = "Rental Operations", description = "Operations related to rental and return the car.")
public class RentalController {

    private final RentalService rentalService;

    @Operation(summary = "Rent a car", description = "Rent a car by providing car ID, customer ID and rental days.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully rented the car",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found resources",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PostMapping("/rent")
    public ResponseEntity<RentalDTO> rentCar(@Valid @RequestBody RentalRequestDTO rentalRequestDTO) {
        return ResponseEntity.ok().body(this.rentalService.rentACar(rentalRequestDTO));
    }

    @Operation(summary = "Return a car", description = "Return a car by providing rental ID and return date.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned the car"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found resources",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PostMapping("/return")
    public ResponseEntity<ReturnResponseDTO> returnCar(@Valid @RequestBody ReturnRequestDTO returnRequestDTO) {
        return ResponseEntity.ok().body(rentalService.returnCar(returnRequestDTO));
    }
}
