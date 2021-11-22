package com.hotel.controller;

import com.hotel.dto.UserDTO;
import com.hotel.model.entity.User;
import com.hotel.service.UserRoleService;
import com.hotel.service.UserService;
import com.hotel.service.UserStatusService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@Builder
public class UserController {
    private UserService userService;
    private UserRoleService userRoleService;
    private UserStatusService userStatusService;
    //temporary version userRole, userStatus for client
    private final Integer clientRoleId = 1;
    private final Integer clientStatusId = 1;

    @GetMapping(value = "/users")
    public String users(Model model) {
        var users = userService.getAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping(value = "/createUsers")
    public String userObjectForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "createUsers";
    }

    @PostMapping(value = "/createUsers")
    public String createUserObject(@ModelAttribute UserDTO userDTO, Model model) {
        var user = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .login(userDTO.getLogin())
                .password(userDTO.getPassword())
                .email(userDTO.getPassword())
                .phoneNumber(userDTO.getPhoneNumber())
                .document(userDTO.getDocument())
                .userRole(userRoleService.getById(clientRoleId))
                .userStatus(userStatusService.getById(clientStatusId))
                .build();
        userService.save(user);
        var users = userService.getAll();
        model.addAttribute("users", users);
        return "users";
    }


    //The old version, will be cleared
    @GetMapping(value = "/create")
    public String userForm() {
        return "createUsersGetType";
    }

    @GetMapping(value = "/createUsersGetType")
    public String createUser(@RequestParam String firstName,
                             @RequestParam String lastName,
                             @RequestParam String login,
                             @RequestParam String password,
                             @RequestParam String email,
                             @RequestParam String phoneNumber,
                             @RequestParam String document,
                             Model model) {
        var user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .login(login)
                .password(password)
                .email(email)
                .phoneNumber(phoneNumber)
                .document(document)
                .userRole(userRoleService.getById(clientRoleId))
                .userStatus(userStatusService.getById(clientStatusId))
                .build();
        userService.save(user);
        var users = userService.getAll();
        model.addAttribute("users", users);
        return "users";
    }
}
