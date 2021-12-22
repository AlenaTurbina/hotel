package com.hotel.model.repository;

import com.hotel.model.entity.ClassApartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassApartmentRepository extends JpaRepository<ClassApartment, Integer> {
}
