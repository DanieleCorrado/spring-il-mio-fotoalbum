package com.example.backend.service;

import com.example.backend.exceptions.CategoryNameUniqueException;
import com.example.backend.model.Category;
import com.example.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.findByOrderByName();
    }

    public Category save(Category category) throws CategoryNameUniqueException {
        // verifico che questo nome non esista già
        if (categoryRepository.existsByName(category.getName())) {
            throw new CategoryNameUniqueException(category.getName());
        }
        // trasformo il nome in lowercase
        category.setName(category.getName().toLowerCase());
        // salvo su database
        return categoryRepository.save(category);
    }
}