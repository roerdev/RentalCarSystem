package com.rentalcar.rental.service.impl;

import com.rentalcar.rental.dto.RentalDTO;
import com.rentalcar.rental.dto.RentalRequestDTO;
import com.rentalcar.rental.dto.ReturnRequestDTO;
import com.rentalcar.rental.dto.ReturnResponseDTO;
import com.rentalcar.rental.mapper.RentalMapper;
import com.rentalcar.rental.model.Car;
import com.rentalcar.rental.model.Rental;
import com.rentalcar.rental.repository.CarRepository;
import com.rentalcar.rental.repository.ClientRepository;
import com.rentalcar.rental.repository.PriceConditionRepository;
import com.rentalcar.rental.repository.RentalRepository;
import com.rentalcar.rental.repository.StatusCarRepository;
import com.rentalcar.rental.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final CarRepository carRepository;
    private final ClientRepository clientRepository;
    private final RentalRepository rentalRepository;
    private final PriceConditionRepository priceConditionRepository;
    private final StatusCarRepository statusCarRepository;
    private final RentalMapper rentalMapper;

    @Override
    public RentalDTO rentACar(RentalRequestDTO rentalRequestDTO) {
        var car = this.carRepository.findById(rentalRequestDTO.getCarId()).orElseThrow(() -> new RuntimeException("Car not found"));
        if(!car.getStatus().getStatus().equals("AVAILABLE")) {
            throw new RuntimeException("Car is already not available for rent");
        }
        var client = this.clientRepository.findById(rentalRequestDTO.getClientId()).orElseThrow(() -> new RuntimeException("Customer not found"));
        var price = this.calculatePrice(car, rentalRequestDTO.getDays());
        var rental = Rental.builder()
                .car(car)
                .client(client)
                .rentalDays(rentalRequestDTO.getDays())
                .startDate(rentalRequestDTO.getCurrencyDate())
                .endDate(rentalRequestDTO.getCurrencyDate().plusDays(rentalRequestDTO.getDays()))
                .price(BigDecimal.valueOf(price))
                .build();
        var points = this.getLoyaltyPoints(car);
        client.setLoyaltyPoints(client.getLoyaltyPoints() + points);
        this.clientRepository.save(client);
        var statusRented = this.statusCarRepository.findIdByStatus("RENTED").orElseThrow(() -> new RuntimeException("Status not found"));
        car.setStatus(statusRented);
        this.carRepository.save(car);

        return this.rentalMapper.rentalToRentalDTO(rentalRepository.save(rental));
    }

    @Override
    public ReturnResponseDTO returnCar(ReturnRequestDTO returnRequestDTO) {
        Rental rental = this.rentalRepository.findById(returnRequestDTO.getRentalId()).orElseThrow(() -> new RuntimeException("Rental not found"));
        Car car = rental.getCar();
        var statusRented = this.statusCarRepository.findIdByStatus("AVAILABLE").orElseThrow(() -> new RuntimeException("Status not found"));
        car.setStatus(statusRented);
        this.carRepository.save(car);
        var extraDays = ChronoUnit.DAYS.between(rental.getEndDate(), returnRequestDTO.getReturnDate());
        double lateFee = 0;
        if (extraDays > 0) {
            lateFee = this.calculateLateFee(car, (int) extraDays);
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

    private double calculatePrice(Car car, int rentalDays) {
        var conditions = priceConditionRepository.findByTypeCar_IdOrderByMinDaysAsc(car.getType().getId());
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

    public double calculateLateFee(Car car, int extraDays) {
        return car.getType().getPriceExtraDay().doubleValue() * extraDays;
    }

    private int getLoyaltyPoints(Car car) {
        return car.getType().getLoyaltyPoints();
    }
}
