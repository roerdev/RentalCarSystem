package com.rentalcar.rental.controller;

import com.rentalcar.rental.dto.CarDTO;
import com.rentalcar.rental.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
@Tag(name = "Car Operations", description = "Operations related to car inventory.")
public class CarController {

    private final CarService carService;

    @Operation(summary = "Get all car", description = "Get all cars with pagination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get all car",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found resources",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<Page<CarDTO>>getAllCars(@PageableDefault(size = 20, page = 0) final Pageable page) {
        return ResponseEntity.ok().body(this.carService.findAllCars(page));
    }

    @Operation(summary = "Get car by ID", description = "Get car by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get a car",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found resources",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.carService.findCarById(id));
    }

    @Operation(summary = "Create a car", description = "Create a car by providing car model, type ID and status ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created a car",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<CarDTO> createCar(@Valid @RequestBody CarDTO car) {
        return ResponseEntity.ok().body(this.carService.saveCar(car));
    }

    @Operation(summary = "Update a car", description = "Update a car by providing car ID, car model, type ID and status ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated a car",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found resources",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<CarDTO> updateCar(@PathVariable Long id, @Valid @RequestBody CarDTO carDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.carService.updateCar(id, carDetails));
    }

    @Operation(summary = "Delete a car", description = "Delete a car by providing car ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted a car"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found resources",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        this.carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}
