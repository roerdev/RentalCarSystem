package com.rentalcar.rental.controller;

import com.rentalcar.rental.dto.CarDTO;
import com.rentalcar.rental.dto.ClientDTO;
import com.rentalcar.rental.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
@Tag(name = "Client Operations", description = "Operations related to clients.")
public class ClientController {

    private final ClientService clientService;

    @Operation(summary = "Get all clients", description = "Get all clients with pagination.")
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
    public ResponseEntity<Page<ClientDTO>>getAllCars(@PageableDefault(size = 20, page = 0) final Pageable page) {
        return ResponseEntity.ok().body(this.clientService.findAllClients(page));
    }

    @Operation(summary = "Get client by ID", description = "Get client by ID.")
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
    public ResponseEntity<ClientDTO> getCarById(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.clientService.getClientById(id));
    }
}
