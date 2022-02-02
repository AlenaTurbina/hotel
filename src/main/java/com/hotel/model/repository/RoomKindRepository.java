package com.hotel.model.repository;

import com.hotel.model.entity.RoomKind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomKindRepository extends JpaRepository<RoomKind, Integer> {

}
