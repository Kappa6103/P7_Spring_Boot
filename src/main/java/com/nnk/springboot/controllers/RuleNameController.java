package com.nnk.springboot.controllers;

import com.nnk.springboot.models.RuleName;
import com.nnk.springboot.services.RuleNameService;
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
public class RuleNameController {

    @Autowired
    RuleNameService service;

    //TODO : wrong http verbs throughout the class, but constrained by the view

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        model.addAttribute("ruleNames", service.fetchAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
        model.addAttribute("ruleName", new RuleName());
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            service.createAndSave(ruleName);
            return "redirect:/ruleName/list";
        }
        model.addAttribute("ruleName", ruleName);
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<RuleName> optRuleName = service.fetchById(id);
        RuleName ruleName;
        if (optRuleName.isPresent()) {
            ruleName = optRuleName.get();
            model.addAttribute("ruleName", ruleName);
            return "ruleName/update";
        } else {
            throw new IllegalArgumentException("Invalid ruleName Id: " + id);
        }
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/update";
        }
        service.updateAndSave(ruleName);
        model.addAttribute("ruleNames", service.fetchAll());
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        if (service.existsById(id)) {
            service.deleteById(id);
        } else {
            throw new IllegalArgumentException("Invalid ruleName Id: " + id);
        }
        model.addAttribute("ruleNames", service.fetchAll());
        return "redirect:/ruleName/list";
    }
}
