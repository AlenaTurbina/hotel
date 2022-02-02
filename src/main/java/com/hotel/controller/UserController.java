package com.hotel.controller;

import com.hotel.model.entity.User;
import com.hotel.service.OrderBookingService;
import com.hotel.service.RoleService;
import com.hotel.service.UserService;
import com.hotel.service.UserStatusService;
import com.hotel.validator.UserClientUpdateValidator;
import com.hotel.validator.UserUpdateValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.hotel.utilit.Constant.MAX_ITEMS_ON_PAGE;


@Controller
@AllArgsConstructor
public class  UserController {
    private UserService userService;
    private RoleService roleService;
    private UserStatusService userStatusService;
    private UserUpdateValidator userUpdateValidator;
    private OrderBookingService orderBookingService;
    private UserClientUpdateValidator userClientUpdateValidator;


    //List of users with pagination /GET/
    @GetMapping(value = "/admin/users")
    public String usersPaginated(Model model) {
        return findPaginated(1, "id", "asc", model);
    }

    //Pagination for all users
    @GetMapping("/admin/users/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") Integer pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        Integer pageSize = MAX_ITEMS_ON_PAGE;

        Page<User> page = userService.findPaginatedAndSorted(pageNo, pageSize, sortField, sortDir);
        List<User> users = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("users", users);
        return "/admin/users";
    }


    //Admin page /GET/
    @GetMapping("/admin")
    public String admin(Authentication authentication, Model model) {
        var user = userService.findLoggedUser(authentication);
        model.addAttribute("user", user);
        var orderBookings = orderBookingService.getListOrderBookingsWithToday();
        model.addAttribute("orderBookings", orderBookings);
        return "/admin/adminPage";
    }


    //Update user /GET, POST/
    @GetMapping("/admin/users/update/{id}")
    public String updateUserForm(@PathVariable("id") Integer id, Model model) {
        var user = userService.getById(id);
        model.addAttribute("user", user);
        var roles = roleService.getAll();
        model.addAttribute("roles", roles);
        var userStatuses = userStatusService.getAll();
        model.addAttribute("userStatuses", userStatuses);
        return "/admin/updateUsers";
    }

    @PostMapping("/admin/users/update")
    public String updateUser(User user, BindingResult bindingResult, Model model) {
        userUpdateValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            var roles = roleService.getAll();
            model.addAttribute("roles", roles);
            var userStatuses = userStatusService.getAll();
            model.addAttribute("userStatuses", userStatuses);
            return "/admin/updateUsers";
        }
        userService.update(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/client/userData")
    public String userData(Authentication authentication, Model model) {
        var user = userService.findLoggedUser(authentication);
        model.addAttribute("user", user);
        return "/client/userClient";
    }

    //Update user on client side with validation /GET, POST/
    @GetMapping("/client/updateForm")
    public String updateUserForm(Authentication authentication, Model model) {
        var user = userService.findLoggedUser(authentication);
        model.addAttribute("user", user);
        return "/client/updateUserClient";
    }

    @PostMapping("/client/updateForm")
    public String updateUser(User user, Model model, BindingResult bindingResult, Authentication authentication) {
        String userEmailBeforeUpdate = userService.findLoggedUser(authentication).getEmail();
        userClientUpdateValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/client/updateUserClient";
        } else {
            User newUser = userService.update(user.getId(), user.getFirstName(), user.getLastName(), user.getDocument(),
                    user.getPhoneNumber(), user.getPassword(), user.getEmail());
            if (newUser.getEmail().equals(userEmailBeforeUpdate)) {
                return "redirect:/client/userData";
            } else {
                authentication.setAuthenticated(false);
                return "redirect:/login";
            }
        }
    }
}



