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
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "sum_total")
    private Integer sumTotal;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_booking", referencedColumnName = "id")
    private OrderBooking orderBooking;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "responsible")
    private User responsible;
}
