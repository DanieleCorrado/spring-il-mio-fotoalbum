package com.example.backend.controller;

import com.example.backend.exceptions.PhotoNotFoundException;
import com.example.backend.model.Photo;
import com.example.backend.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @GetMapping
    public String index(@RequestParam Optional<String> search, Model model) {
        model.addAttribute("photosList", photoService.getPhotoList(search));

        return "photos/list";
    }

    // metodo che mostra i dettagli di una foto preso per id
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
}
