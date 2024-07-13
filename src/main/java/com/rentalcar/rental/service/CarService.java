package com.rentalcar.rental.service;

import com.rentalcar.rental.dto.CarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarService {

    Page<CarDTO> findAllCars(Pageable page);
    CarDTO findCarById(Long id);
    CarDTO saveCar(CarDTO car);
    void deleteCar(Long id);
    CarDTO updateCar(Long id, CarDTO carDetails);
    CarDTO updateStatusCar(Long id, String status);
}
