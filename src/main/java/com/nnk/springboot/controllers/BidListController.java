package com.nnk.springboot.controllers;

import com.nnk.springboot.models.BidList;
import com.nnk.springboot.services.BidListService;
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
public class BidListController {

    @Autowired
    BidListService bidListService;

    //TODO : wrong http verbs throughout the class, but constrained by the view

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute("bidLists", bidListService.fetchAll());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        model.addAttribute("bidList", new BidList());
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            bidListService.createAndSave(bid);
            return "redirect:/bidList/list";
        }
        model.addAttribute("bidList", bid);
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<BidList> optBidList = bidListService.fetchById(id);
        BidList bidList;
        if (optBidList.isPresent()) {
            bidList = optBidList.get();
            model.addAttribute("bidList", bidList);
            return "bidList/update";
        } else {
            throw new IllegalArgumentException("Invalid bidList Id: " + id);
        }
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update BidList and return list BidList
        if (result.hasErrors()) {
            return "bidList/update";
        }
        bidListService.updateAndSave(bidList);
        model.addAttribute("bidLists", bidListService.fetchAll());
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find BidList by Id and delete the bid, return to BidList list
        if (bidListService.existsById(id)) {
            bidListService.deleteById(id);
        } else {
            throw new IllegalArgumentException("Invalid bidList Id: " + id);
        }
        model.addAttribute("bidLists", bidListService.fetchAll());
        return "redirect:/bidList/list";
    }
}
