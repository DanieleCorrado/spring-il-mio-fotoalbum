package com.example.backend.service;

import com.example.backend.exceptions.PhotoNotFoundException;
import com.example.backend.model.Photo;
import com.example.backend.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    // Metodo che resituisce la lista di tutte le foto eventualmente filtrate

    public List<Photo> getPhotoList(Optional<String> search){
        if (search.isPresent()) {
            return photoRepository.findByTitleContainingIgnoreCase(search.get());
        } else {
            return photoRepository.findAll();
        }
    }

    // metodo che restituisce un libro preso per id, se non lo trova solleva un'eccezione
    public Photo getPhotoById(Integer id) throws PhotoNotFoundException {
        Optional<Photo> result = photoRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new PhotoNotFoundException("Photo with id " + id + " not found");
        }
    }
}
