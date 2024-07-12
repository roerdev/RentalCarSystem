package com.rentalcar.rental.mapper;

import com.rentalcar.rental.dto.CarDTO;
import com.rentalcar.rental.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CarMapper {

    CarDTO carToCarDTO(Car car);
    Car carDTOToCar(CarDTO carDTO);
}
