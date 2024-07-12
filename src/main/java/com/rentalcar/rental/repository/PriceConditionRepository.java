package com.rentalcar.rental.repository;

import com.rentalcar.rental.model.PriceCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceConditionRepository extends JpaRepository<PriceCondition, Long> {
    List<PriceCondition> findByTypeCar_IdOrderByMinDaysAsc(Long typeCarId);
}
