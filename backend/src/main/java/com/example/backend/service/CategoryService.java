package com.example.backend.service;

import com.example.backend.exceptions.CategoryNameUniqueException;
import com.example.backend.exceptions.PhotoNotFoundException;
import com.example.backend.model.Category;
import com.example.backend.model.Photo;
import com.example.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    // Metodo che restituisce una categoria preso per id, se non la trova solleva un'eccezione
    public Category getCategoryById(Integer id) throws PhotoNotFoundException {
        Optional<Category> result = categoryRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new PhotoNotFoundException("Photo with id " + id + " not found");
        }
    }

    // Metodo che elimina una foto da database
    public void deleteCategory(Integer id) {
        Category categoryToDelete = getCategoryById(id);
        List<Photo> photoList = categoryToDelete.getPhotos();
        if (!photoList.isEmpty()) {
            for (Photo photo: photoList) {
                List<Category> categories = photo.getCategories();
                categories.remove(categoryToDelete);
            }
            categoryToDelete.setPhotos(new ArrayList<>());
        }
        categoryRepository.deleteById(categoryToDelete.getId());
    }
}