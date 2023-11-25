package com.example.backend.controller;

import com.example.backend.exceptions.CategoryNameUniqueException;
import com.example.backend.exceptions.PhotoNotFoundException;
import com.example.backend.model.Category;
import com.example.backend.model.Photo;
import com.example.backend.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    // Metodo che recupera la lista di categorie dal database
    @GetMapping
    public String index(Model model) {
        // passa al model categoryList con la lista di categorie

        model.addAttribute("categoryList", categoryService.getAll());
        // passa al model una categoria vuota come attributo categoryObj del form

        model.addAttribute("categoryObj", new Category());
        return "categories/index";
    }

    // Metodo che aggiunge una categoria al database
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

            return "categories/index";
        }
    }

    // Metodo che elimina una categoria da database
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes){

        try {
            // Recupera la categoria con l'id specificato

            Category categoryToDelete = categoryService.getCategoryById(id);

            // Elimina la categoria dal database utilizzando il CategoryService

            categoryService.deleteCategory(id);
            redirectAttributes.addFlashAttribute("message", "Category " + categoryToDelete.getName() + " deleted");

            return "redirect:/categories";
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}