package com.hotel.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column(name = "room_price")
    private Double roomPrice;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "class_apartment")
    private ClassApartment classApartment;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_type")
    private RoomType roomType;

}
