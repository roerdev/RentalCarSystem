package com.rentalcar.rental.service;

import com.rentalcar.rental.dto.RentalDTO;
import com.rentalcar.rental.dto.RentalRequestDTO;
import com.rentalcar.rental.dto.ReturnRequestDTO;
import com.rentalcar.rental.dto.ReturnResponseDTO;

public interface RentalService {

    RentalDTO rentACar(RentalRequestDTO rentalRequestDTO);
    ReturnResponseDTO returnCar(ReturnRequestDTO returnRequestDTO);


}
