package spring_mvc_and_hiber.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring_mvc_and_hiber.models.Role;
import spring_mvc_and_hiber.models.User;
import spring_mvc_and_hiber.services.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("one_user", userService.show(id));
        return "user/show";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable("id") Integer id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    //этот метод для того чтобы у юзера был доступ только к своей странице к своему id
    public boolean hasUserId(Authentication authentication, int userId) {
        User user = (User) authentication.getPrincipal();
        return user.getId() == userId;
    }

    @GetMapping(value = "/{id}/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("edit_user", userService.show(id));
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String update(@RequestParam(name = "isAdmin", required = false) boolean isAdmin,
                         @RequestParam(name = "isUser", required = false) boolean isUser,
                         @ModelAttribute("update_user") User user, @PathVariable("id") int id) {
        Set<Role> rolesToAdd = new HashSet<>();

        if (isUser) {
            rolesToAdd.add(new Role(2, "ROLE_USER"));
        }
        if (isAdmin) {
            rolesToAdd.add(new Role(1, "ROLE_ADMIN"));
        }

        user.setRoles(rolesToAdd);
        userService.update(id, user);
        return "redirect:/admin";
    }
}
