package com.rentalcar.rental.service.impl;

import com.rentalcar.rental.dto.CarDTO;
import com.rentalcar.rental.exception.NotFoundException;
import com.rentalcar.rental.mapper.CarMapper;
import com.rentalcar.rental.model.Car;
import com.rentalcar.rental.model.StatusCar;
import com.rentalcar.rental.model.TypeCar;
import com.rentalcar.rental.repository.CarRepository;
import com.rentalcar.rental.repository.StatusCarRepository;
import com.rentalcar.rental.repository.TypeCarRepository;
import com.rentalcar.rental.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.rentalcar.rental.util.constants.CommonConstants.CAR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final TypeCarRepository typeCarRepository;
    private final StatusCarRepository statusCarRepository;
    private final CarMapper carMapper;

    @Override
    public Page<CarDTO> findAllCars(Pageable page) {
        return this.carRepository.findAll(page).map(carMapper::carToCarDTO);
    }

    @Override
    public CarDTO findCarById(Long id) {
        var car = this.carRepository.findById(id).orElseThrow(() -> new NotFoundException(CAR_NOT_FOUND));
        return this.carMapper.carToCarDTO(car);
    }

    @Override
    @Transactional
    public CarDTO saveCar(CarDTO car) {
        var carEntity = this.carMapper.carDTOToCar(car);
        var type = this.typeCarRepository.findById(carEntity.getType().getId()).orElseThrow(() -> new NotFoundException("Type not found for this id :: " + carEntity.getType().getId()));
        var status = this.statusCarRepository.findById(carEntity.getStatus().getId()).orElseThrow(() -> new NotFoundException("Status not found for this id :: " + carEntity.getStatus().getId()));
        carEntity.setType(type);
        carEntity.setStatus(status);
        return this.carMapper.carToCarDTO(this.carRepository.save(carEntity));
    }

    @Override
    @Transactional
    public void deleteCar(Long id) {
        var car = this.carRepository.findById(id).orElseThrow(() -> new NotFoundException(CAR_NOT_FOUND));
        this.carRepository.delete(car);
    }

    @Override
    @Transactional
    public CarDTO updateCar(Long id, CarDTO carDetails) {
        Car car = carRepository.findById(id).orElseThrow(() -> new RuntimeException("Car not found for this id :: " + id));
        TypeCar typeCar = typeCarRepository.findById(carDetails.getType().getId()).orElseThrow(() -> new NotFoundException("Type not found for this id :: " + carDetails.getType().getId()));
        StatusCar statusCar = statusCarRepository.findById(carDetails.getStatus().getId()).orElseThrow(() -> new NotFoundException("Status not found for this id :: " + carDetails.getStatus().getId()));

        car.setModel(carDetails.getModel());
        car.setType(typeCar);
        car.setStatus(statusCar);
        return this.carMapper.carToCarDTO(carRepository.save(car));
    }

    @Override
    @Transactional
    public CarDTO updateStatusCar(Long id, String status) {
        var car = this.carRepository.findById(id).orElseThrow(() -> new NotFoundException(CAR_NOT_FOUND));
        var statusCar = this.statusCarRepository.findIdByStatus(status).orElseThrow(() -> new NotFoundException("Status not found"));
        car.setStatus(statusCar);
        return this.carMapper.carToCarDTO(carRepository.save(car));
    }
}
