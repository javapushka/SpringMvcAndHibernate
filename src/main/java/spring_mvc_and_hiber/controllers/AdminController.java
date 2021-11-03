package spring_mvc_and_hiber.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import spring_mvc_and_hiber.models.Role;
import spring_mvc_and_hiber.models.User;
import spring_mvc_and_hiber.services.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("all_users", userService.index());
        return "admin/index";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("new_user") User user) {
        return "admin/new";
    }

    @PostMapping()
    public String create(@RequestParam(name = "isAdmin", required = false) boolean isAdmin,
                         @RequestParam(name = "isUser", required = false) boolean isUser,
                         @ModelAttribute User user) throws Exception {
        Set<Role> rolesToAdd = new HashSet<>();

        if (userService.getUserByName(user.getName()) != null) {
            throw new Exception("======================User exists!======================");
        }
        if (isUser) {
            rolesToAdd.add(new Role(2, "ROLE_USER"));
        }
        if (isAdmin) {
            rolesToAdd.add(new Role(1, "ROLE_ADMIN"));
        }

        user.setRoles(rolesToAdd);
        userService.save(user);
        return "redirect:/admin";
    }
}
