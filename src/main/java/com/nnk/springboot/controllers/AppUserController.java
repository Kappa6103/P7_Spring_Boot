package com.nnk.springboot.controllers;

import com.nnk.springboot.models.AppUser;
import com.nnk.springboot.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
public class AppUserController {
    @Autowired
    private AppUserRepository userRepository;

    @RequestMapping("/user/list")
    public String home(Model model)
    {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(AppUser bid) {
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid AppUser appUser, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            appUser.setPassword(encoder.encode(appUser.getPassword()));
            userRepository.save(appUser);
            model.addAttribute("users", userRepository.findAll());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        AppUser appUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        appUser.setPassword("");
        model.addAttribute("user", appUser);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid AppUser appUser,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        appUser.setPassword(encoder.encode(appUser.getPassword()));
        appUser.setAppUserId(id);
        userRepository.save(appUser);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        AppUser appUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(appUser);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }
}
