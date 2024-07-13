package com.rentalcar.rental.mapper;

import com.rentalcar.rental.dto.StatusCarDTO;
import com.rentalcar.rental.model.StatusCar;
import org.mapstruct.Mapper;

@Mapper
public interface StatusCarMapper {

    StatusCarDTO statusCarToStatusCarDTO(StatusCar statusCar);
    StatusCar statusCarDTOToStatusCar(StatusCarDTO statusCarDTO);
}
