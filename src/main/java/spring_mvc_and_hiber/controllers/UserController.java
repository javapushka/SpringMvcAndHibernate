package spring_mvc_and_hiber.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import spring_mvc_and_hiber.models.User;
import spring_mvc_and_hiber.services.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("all_users", userService.index());
        return "users/index";
    }

    @GetMapping("/{id}")
    public String snow(@PathVariable("id") int id, Model model) {
        model.addAttribute("one_user", userService.show(id));
        return "users/show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("new_user") User user) {
        return "users/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("create_user") User user) {
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("edit_user", userService.show(id));
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("update_user") User user, @PathVariable("id") int id) {
        userService.update(id, user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/users";
    }


//    @GetMapping("/new")
//    public String newUser(Model model) {
//        model.addAttribute("new_user", new User());
//        return "users/new";
//    }
//
//    @PostMapping()
//    public String create(@RequestParam("name") String name, @RequestParam("age") String age, Model model) {
//        User user = new User();
//        user.setAge("23");
//        user.setName("Adam");
//
//        userDaoImp.save(user);
//
//        model.addAttribute("create_user",user);
//        return "redirect:/users";
//    }
}
