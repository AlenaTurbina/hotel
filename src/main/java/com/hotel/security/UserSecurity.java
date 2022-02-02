package com.hotel.security;

import com.hotel.model.entity.Role;
import com.hotel.model.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.hotel.utilit.Constant.ID_DEFAULT_USER_STATUS_ACTIVE;


@Data
public class UserSecurity implements UserDetails {

    private final String username;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;
    private final boolean isActive;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }


    //Transformation Entity User into UserDetails
    public static UserDetails fromUser(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),                                                        //username==email
                user.getPassword(),                                                     //password
                user.getUserStatus().getId().equals(ID_DEFAULT_USER_STATUS_ACTIVE),     //enabled
                user.getUserStatus().getId().equals(ID_DEFAULT_USER_STATUS_ACTIVE),     //accountNonExpired
                user.getUserStatus().getId().equals(ID_DEFAULT_USER_STATUS_ACTIVE),     //credentialsNonExpired
                user.getUserStatus().getId().equals(ID_DEFAULT_USER_STATUS_ACTIVE),     //accountNonLocked
                mapRolesToAuthorities(user.getRoles()));                                //Collection authorities
    }

    //Method for getting Collection authorities
    private static Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
