package com.hotel.model.repository;

import com.hotel.model.entity.OrderBooking;
import com.hotel.model.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderBookingRepository extends JpaRepository<OrderBooking, Integer> {

    List<OrderBooking> findAllByClient(User user, Pageable pageable);

    List<OrderBooking> findAllByClient(User user);
}
