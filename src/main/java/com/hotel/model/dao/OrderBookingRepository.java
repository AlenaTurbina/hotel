package com.hotel.model.dao;

import com.hotel.model.entity.OrderBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderBookingRepository extends JpaRepository<OrderBooking, Integer> {
}
