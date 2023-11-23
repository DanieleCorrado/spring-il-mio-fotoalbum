package com.example.backend.controller;

import com.example.backend.exceptions.CategoryNameUniqueException;
import com.example.backend.model.Category;
import com.example.backend.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public String index(Model model) {
        // passa al model categoryList con la lista di categorie
        model.addAttribute("categoryList", categoryService.getAll());
        // passa al model una categoria vuota come attributo categoryObj del form
        model.addAttribute("categoryObj", new Category());
        return "categories/index";
    }

    @PostMapping
    public String doSave(@Valid @ModelAttribute("categoryObj") Category formCategory,
                         BindingResult bindingResult,
                         Model model) {
        // valido la categoria
        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryList", categoryService.getAll());
            return "categories/index";
        }
        try {
            // salvo la nuova categoria su database
            categoryService.save(formCategory);
            return "redirect:/categories";
        } catch (CategoryNameUniqueException e) {
            bindingResult.addError(new FieldError("category", "name", e.getMessage(), false, null, null,
                    "Category name must be unique"));
            model.addAttribute("categoryList", categoryService.getAll());
            // ti rimando alla pagina col form
            return "categories/index";
        }
    }
}