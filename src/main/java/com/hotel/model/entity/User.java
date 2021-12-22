package com.hotel.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    @Column
    private String email;

    @Column
    private String password;


    @Column(name = "first_name")
    private String firstName;


    @Column(name = "last_name")
    private String lastName;
    @Transient
    transient private String confirmPassword;
//    @Column
//    private String login;
//    @Column(name = "phone_number")
//    private String phoneNumber;
//    @Column
//    private String document;
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_role")
//    private Role userRole;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_status")
    private UserStatus userStatus;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<OrderBooking> orderBookings;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "User_With_Role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    List<Role> roles;

}
