package com.rentalcar.rental.repository;

import com.rentalcar.rental.model.StatusCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusCarRepository extends JpaRepository<StatusCar, Long> {

    Optional<StatusCar> findIdByStatus(String status);
}
