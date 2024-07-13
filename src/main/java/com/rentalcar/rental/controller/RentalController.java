package com.rentalcar.rental.controller;

import com.rentalcar.rental.dto.RentalDTO;
import com.rentalcar.rental.dto.RentalRequestDTO;
import com.rentalcar.rental.dto.ReturnRequestDTO;
import com.rentalcar.rental.dto.ReturnResponseDTO;
import com.rentalcar.rental.service.RentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @PostMapping("/rent")
    public ResponseEntity<RentalDTO> rentCar(@Valid @RequestBody RentalRequestDTO rentalRequestDTO) {
        return ResponseEntity.ok().body(this.rentalService.rentACar(rentalRequestDTO));
    }

    @PostMapping("/return")
    public ResponseEntity<ReturnResponseDTO> returnCar(@Valid @RequestBody ReturnRequestDTO returnRequestDTO) {
        return ResponseEntity.ok().body(rentalService.returnCar(returnRequestDTO));
    }
}
