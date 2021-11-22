package com.hotel.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column
    private String login;
    @Column
    private String password;
    @Column
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column
    private String document;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_role")
    private UserRole userRole;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_status")
    private UserStatus userStatus;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<OrderBooking> orderBookings;
    @OneToMany(mappedBy = "responsible", cascade = CascadeType.ALL)
    private List<Invoice> invoices;
}
