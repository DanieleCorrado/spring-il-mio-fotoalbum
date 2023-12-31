package com.example.backend.controller;

import com.example.backend.dto.PhotoDto;
import com.example.backend.exceptions.PhotoNotFoundException;
import com.example.backend.model.Photo;
import com.example.backend.service.CategoryService;
import com.example.backend.service.PhotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private CategoryService categoryService;

    // Metodo che mostra tutte le foto presenti nel database
    @GetMapping
    public String index(@RequestParam Optional<String> search, Model model) {
        model.addAttribute("photosList", photoService.getPhotoList(search));

        return "photos/list";
    }

    // Metodo che mostra i dettagli di una foto presa per id
    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        try {
            Photo photo = photoService.getPhotoById(id);
            model.addAttribute("photo", photo);

            return "photos/show";
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Metodo che mostra il form di creazione della foto
    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("photo", new PhotoDto());
        model.addAttribute("categoryList", categoryService.getAll());

        return "photos/form";
    }

    // Metodo che salva la foto su database
    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute("photo") PhotoDto formPhoto, BindingResult bindingResult, Model model, @RequestParam("photo") MultipartFile photo) {

        if (photo.getSize() == 0) {
            bindingResult.addError(new FieldError("photo", "photo", null, false, null, null,
                    "Photo must be uploaded"));
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryList",categoryService.getAll());

            return "photos/form";
        }


        try {
            Photo savedPhoto = photoService.createPhoto(formPhoto);

            return "redirect:/photos/show/" + savedPhoto.getId();
        } catch (IOException e) {
            bindingResult.addError(new FieldError("photo", "photo", null, false, null, null,
                    "Unable to save file"));

            return "photos/form";
        }
    }

    // Metodo che mostra la pagina di modifica di una foto
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        try {

            model.addAttribute("photo", photoService.getPhotoDtoById(id));
            model.addAttribute("categoryList", categoryService.getAll());

            return "/photos/form";
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // metodo che riceve il submit del form di edit e salva le modifiche alla foto
    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Integer id, @Valid @ModelAttribute("photo") PhotoDto formPhoto,
                         BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryList", categoryService.getAll());

            return "/photos/form";
        }
        try {
            Photo savedPhoto = photoService.editPhoto(formPhoto);

            return "redirect:/photos/show/" + savedPhoto.getId();
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IOException e) {
            bindingResult.addError(new FieldError("photo", "photoFile", null, false, null, null,
                    "Unable to save file"));

            return "photos/form";
        }
    }

    // Metodo che elimina una foto da database
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes){

        try {
            Photo photoToDelete = photoService.getPhotoById(id);
            photoService.deletePhoto(id);
            redirectAttributes.addFlashAttribute("message", "Photo " + photoToDelete.getTitle() + " deleted");

            return "redirect:/photos";
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Metodo che modifica la visibilità di una foto
    @PostMapping("/visibility/{id}")
    public String visibility(@PathVariable Integer id) {

        try {
            Photo photoToEdit = photoService.getPhotoById(id);
            photoService.visibility(photoToEdit);

            return "redirect:/photos";
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
