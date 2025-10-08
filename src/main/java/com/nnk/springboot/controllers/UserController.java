package com.nnk.springboot.controllers;

import com.nnk.springboot.models.User;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/user/list")
    public String home(Model model) {

        model.addAttribute("users", userService.fetchAllUsers());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            userService.createAndSave(user);
            return "redirect:/user/list";
        }
        model.addAttribute("user", user);
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<User> optUser = userService.fetchById(id);
        User user;
        if (optUser.isPresent()) {
            user = optUser.get();
            user.setPassword("");
            model.addAttribute("user", user);
            return "user/update";
        } else {
            throw new IllegalArgumentException("Invalid user Id:" + id);
        }
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }
        userService.updateAndSave(user);
        model.addAttribute("users", userService.fetchAllUsers());
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        if (userService.existsById(id)) {
            userService.deleteById(id);
        } else {
            throw new IllegalArgumentException("Invalid user Id:" + id);
        }
        model.addAttribute("users", userService.fetchAllUsers());
        return "redirect:/user/list";
    }

}
