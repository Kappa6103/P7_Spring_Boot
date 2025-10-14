package com.nnk.springboot.controllers;

import com.nnk.springboot.models.Trade;
import com.nnk.springboot.services.TradeService;
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
public class TradeController {

    @Autowired
    TradeService service;

    //TODO : wrong http verbs throughout the class, but constrained by the view

    @RequestMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("trades", service.fetchAll());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Model model) {
        model.addAttribute("trade", new Trade());
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            service.createAndSave(trade);
            return "redirect:/trade/list";
        }
        model.addAttribute("trade", trade);
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<Trade> optTrade = service.fetchById(id);
        Trade trade;
        if (optTrade.isPresent()) {
            trade = optTrade.get();
            model.addAttribute("trade", trade);
            return "trade/update";
        } else {
            throw new IllegalArgumentException("Invalid trade Id: " + id);
        }
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/update";
        }
        service.updateAndSave(trade);
        model.addAttribute("trades", service.fetchAll());
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        if (service.existsById(id)) {
            service.deleteById(id);
        } else {
            throw new IllegalArgumentException("Invalid trade Id: " + id);
        }
        model.addAttribute("trades", service.fetchAll());
        return "redirect:/trade/list";
    }
}
