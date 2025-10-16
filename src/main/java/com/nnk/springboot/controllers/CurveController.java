package com.nnk.springboot.controllers;

import com.nnk.springboot.models.CurvePoint;
import com.nnk.springboot.services.CurveService;
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
public class CurveController {

    @Autowired
    CurveService curveService;

    //TODO : wrong http verbs throughout the class, but constrained by the view

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("curvePoints", curveService.fetchAll());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(Model model) {
        model.addAttribute("curvePoint", new CurvePoint());
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            curveService.createAndSave(curvePoint); //POINT A
            return "redirect:/curvePoint/list";
        }
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<CurvePoint> optCurvePoint = curveService.fetchById(id);
        CurvePoint curvePoint;
        if (optCurvePoint.isPresent()) {
            curvePoint = optCurvePoint.get();
            model.addAttribute("curvePoint", curvePoint);
            return "curvePoint/update";
        } else {
            throw new IllegalArgumentException("Invalid curvePoint Id: " + id);
        }
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/update";
        }
        curveService.updateAndSave(curvePoint);
        model.addAttribute("curvePoints", curveService.fetchAll()); //TODO : IS THIS STATEMENT NECESSARY ? SEE POINT A
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
        if (curveService.existsById(id)) {
            curveService.deleteById(id);
        } else {
            throw new IllegalArgumentException("Invalid curvePoint Id: " + id);
        }
        model.addAttribute("curvePoints", curveService.fetchAll()); //TODO : IS THIS STATEMENT NECESSARY ? SEE POINT A
        return "redirect:/curvePoint/list";
    }
}
