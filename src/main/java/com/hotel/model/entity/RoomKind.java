package com.hotel.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Room_kind")
public class RoomKind {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "room_price")
    private Double roomPrice;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "class_apartment_id")
    private ClassApartment classApartment;

}
