package com.hotel.model.dao;

import com.hotel.model.entity.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionalRepository extends JpaRepository<Optional, Integer> {
}
