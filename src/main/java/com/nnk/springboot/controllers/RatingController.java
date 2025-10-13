package com.nnk.springboot.controllers;

import com.nnk.springboot.models.Rating;
import com.nnk.springboot.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import java.util.Optional;

@Controller
public class RatingController {

    @Autowired
    RatingService service;

    //TODO : wrong http verbs throughout the class, but constrained by the view

    @RequestMapping("/rating/list")
    public String home(Model model) {
        model.addAttribute("ratings", service.fetchAll());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        model.addAttribute("rating", new Rating());
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            service.createAndSave(rating);
            return "redirect:/rating/list";
        }
        model.addAttribute("rating", rating);
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<Rating> optRating = service.fetchById(id);
        Rating rating;
        if (optRating.isPresent()) {
            rating = optRating.get();
            model.addAttribute("rating", rating);
            return "rating/update";
        } else {
            throw new IllegalArgumentException("Invalid rating Id: " + id);
        }
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/update";
        }
        service.updateAndSave(rating);
        model.addAttribute("ratings", service.fetchAll());
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        if (service.existsById(id)) {
            service.deleteById(id);
        } else {
            throw new IllegalArgumentException("Invalid rating Id: " + id);
        }
        model.addAttribute("ratings", service.fetchAll());
        return "redirect:/rating/list";
    }
}
