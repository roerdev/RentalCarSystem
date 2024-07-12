package com.rentalcar.rental.mapper;

import com.rentalcar.rental.dto.TypeCarDTO;
import com.rentalcar.rental.model.TypeCar;
import org.mapstruct.Mapper;

@Mapper
public interface TypeCarMapper {

    TypeCarDTO typeCarToTypeCarDTO(TypeCar typeCar);
    TypeCar typeCarDTOToTypeCar(TypeCarDTO typeCarDTO);
}
