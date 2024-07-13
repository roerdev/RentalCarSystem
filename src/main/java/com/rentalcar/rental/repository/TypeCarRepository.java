package com.rentalcar.rental.repository;

import com.rentalcar.rental.model.TypeCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeCarRepository extends JpaRepository<TypeCar, Long> {
}
