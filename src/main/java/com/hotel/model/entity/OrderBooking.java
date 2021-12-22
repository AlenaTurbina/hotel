package com.hotel.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "Order_booking")
public class OrderBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column
    private LocalDate date;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_arrival")
    private LocalDate dateArrival;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_departure")
    private LocalDate dateDeparture;
    @Column(name = "quantity_persons")
    private Integer quantityPersons;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client")
    private User client;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room")
    private Room room;
    @Column(name = "sum_total")
    private Double sumTotal;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_status")
    private OrderStatus orderStatus;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Order_Booking_With_Optional",
            joinColumns = @JoinColumn(name = "order_booking_id"),
            inverseJoinColumns = @JoinColumn(name = "optional_id")
    )
    List<Optional> optionals;

}
