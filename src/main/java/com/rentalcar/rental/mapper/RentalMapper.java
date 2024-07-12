package com.rentalcar.rental.mapper;

import com.rentalcar.rental.dto.RentalDTO;
import com.rentalcar.rental.model.Rental;
import org.mapstruct.Mapper;

@Mapper
public interface RentalMapper {

    RentalDTO rentalToRentalDTO(Rental rental);
    Rental rentalDTOToRental(RentalDTO rentalDTO);
}
