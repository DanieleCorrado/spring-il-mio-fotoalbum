package com.example.backend.controller;

import com.example.backend.exceptions.PhotoNotFoundException;
import com.example.backend.model.Photo;
import com.example.backend.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/files")
public class FileController {

    @Autowired
    private PhotoService photoService;

    // Metodo che restituisce la foto in base all'id
    @GetMapping("/photo/{photoId}")
    public ResponseEntity<byte[]> servePhoto(@PathVariable Integer photoId) {

        try {
            Photo photo = photoService.getPhotoById(photoId);
            byte[] photobytes = photo.getPhoto();

            if (photobytes != null && photobytes.length > 0) {
                MediaType mediaType = MediaType.IMAGE_JPEG;

                return ResponseEntity.ok().contentType(mediaType).body(photobytes);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (PhotoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
