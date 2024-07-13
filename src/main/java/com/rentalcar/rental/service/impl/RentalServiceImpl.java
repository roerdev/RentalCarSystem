package com.rentalcar.rental.service.impl;

import com.rentalcar.rental.dto.CarDTO;
import com.rentalcar.rental.dto.RentalDTO;
import com.rentalcar.rental.dto.RentalRequestDTO;
import com.rentalcar.rental.dto.ReturnRequestDTO;
import com.rentalcar.rental.dto.ReturnResponseDTO;
import com.rentalcar.rental.exception.NotFoundException;
import com.rentalcar.rental.exception.ValidationException;
import com.rentalcar.rental.mapper.RentalMapper;
import com.rentalcar.rental.model.Car;
import com.rentalcar.rental.model.Client;
import com.rentalcar.rental.model.Rental;
import com.rentalcar.rental.repository.PriceConditionRepository;
import com.rentalcar.rental.repository.RentalRepository;
import com.rentalcar.rental.service.CarService;
import com.rentalcar.rental.service.ClientService;
import com.rentalcar.rental.service.RentalService;
import com.rentalcar.rental.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

import static com.rentalcar.rental.util.constants.CommonConstants.STATUS_AVAILABLE;
import static com.rentalcar.rental.util.constants.CommonConstants.STATUS_RENTED;

@Service
@Transactional
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final PriceConditionRepository priceConditionRepository;
    private final ClientService clientService;
    private final CarService carService;
    private final RentalMapper rentalMapper;

    @Override
    public RentalDTO rentACar(RentalRequestDTO rentalRequestDTO) {
        var car = this.carService.findCarById(rentalRequestDTO.getCarId());
        if(!car.getStatus().getStatus().equals(STATUS_AVAILABLE)) {
            throw new ValidationException("Car is already not available for rent");
        }
        var price = this.calculatePrice(car, rentalRequestDTO.getDays());
        var points = this.getLoyaltyPoints(car);
        var carDTO = this.carService.updateStatusCar(car.getId(), STATUS_RENTED);
        var clientDTO = this.clientService.updatePoints(rentalRequestDTO.getClientId(), points);
        var rental = Rental.builder()
                .car(Car.builder().id(carDTO.getId()).build())
                .client(Client.builder().id(clientDTO.getId()).build())
                .rentalDays(rentalRequestDTO.getDays())
                .startDate(rentalRequestDTO.getCurrencyDate())
                .endDate(DateUtil.addDays(rentalRequestDTO.getCurrencyDate(), rentalRequestDTO.getDays()))
                .price(BigDecimal.valueOf(price))
                .build();

        return this.rentalMapper.rentalToRentalDTO(rentalRepository.save(rental));
    }

    @Override
    public ReturnResponseDTO returnCar(ReturnRequestDTO returnRequestDTO) {
        Rental rental = this.rentalRepository.findById(returnRequestDTO.getRentalId()).orElseThrow(() -> new NotFoundException("Rental not found"));
        var car = this.carService.updateStatusCar(rental.getCar().getId(), STATUS_AVAILABLE);

        var extraDays = DateUtil.daysBetween(rental.getEndDate(), returnRequestDTO.getReturnDate());
        double lateFee = 0;
        if (extraDays > 0) {
            lateFee = this.calculatePriceExtraDay(rental.getCar(), (int) extraDays);
        }

        return ReturnResponseDTO.builder()
                .rentalId(rental.getId())
                .carId(car.getId())
                .model(car.getModel())
                .type(car.getType().getName())
                .returnDate(returnRequestDTO.getReturnDate())
                .loyaltyPoints(car.getType().getLoyaltyPoints())
                .totalPoints(rental.getClient().getLoyaltyPoints())
                .priceExtraDay(BigDecimal.valueOf(lateFee))
                .build();
    }

    private double calculatePrice(CarDTO car, int rentalDays) {
        var conditions = priceConditionRepository.findByTypeCar_IdOrderByMinDaysAsc(car.getType().getId());
        if(conditions.isEmpty()) {
            throw new NotFoundException("Price conditions not found");
        }
        double price = 0;
        int maxDays = 0;
        int minDays = 0;
        int daysInThisRange = 0;
        var daysLeft = rentalDays;
        for (var condition : conditions) {
            if (daysLeft > 0) {
                maxDays = Objects.nonNull(condition.getMaxDays()) ? condition.getMaxDays() : daysLeft;
                minDays = Objects.nonNull(condition.getMaxDays()) ? condition.getMinDays() : 1;
                daysInThisRange = Math.min(daysLeft, (maxDays - minDays) + 1);
                price += daysInThisRange * condition.getTypeCar().getPrice().doubleValue() * (1 - condition.getDiscount());
                daysLeft -= daysInThisRange;
            }
        }

        return price;
    }

    public double calculatePriceExtraDay(Car car, int extraDays) {
        return car.getType().getPriceExtraDay().doubleValue() * extraDays;
    }

    private int getLoyaltyPoints(CarDTO car) {
        return car.getType().getLoyaltyPoints();
    }
}
