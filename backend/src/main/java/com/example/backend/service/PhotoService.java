package com.example.backend.service;

import com.example.backend.dto.PhotoDto;
import com.example.backend.exceptions.PhotoNotFoundException;
import com.example.backend.model.Photo;
import com.example.backend.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    // Metodo che restituisce la lista di tutte le foto eventualmente filtrate
    public List<Photo> getPhotoList(Optional<String> search){
        if (search.isPresent()) {
            return photoRepository.findByTitleContainingIgnoreCase(search.get());
        }else {
            return photoRepository.findAll();
        }
    }

    // Metodo che restituisce solo le foto con visibilità public

    public List<Photo> getVisiblePhotoList(Optional<String> search){
        if (search.isPresent()) {
            return photoRepository.findByTitleContainingIgnoreCaseAndVisibilityTrue(search.get());
        }else {
            return photoRepository.findByVisibilityTrue();
        }
    }

    // Metodo che restituisce una foto preso per id, se non lo trova solleva un'eccezione

    public Photo getPhotoById(Integer id) throws PhotoNotFoundException {
        Optional<Photo> result = photoRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new PhotoNotFoundException("Photo with id " + id + " not found");
        }
    }

    // Metodo che crea una foto nuova
    public Photo createPhoto(Photo photo) {

        photo.setId(null);

        return photoRepository.save(photo);

    }

    public Photo createPhoto(PhotoDto photoDto) throws IOException {

        Photo photo = convertDtoToPhoto(photoDto);

        return createPhoto(photo);
    }

    // Metodo che permette la conversione da photoDto a photo

    private static Photo convertDtoToPhoto(PhotoDto photoDto) throws IOException {

        Photo photo = new Photo();
        photo.setTitle(photoDto.getTitle());
        photo.setVisibility(photoDto.isVisibility());
        photo.setCategories(photoDto.getCategories());
        photo.setDescription(photoDto.getDescription());
        photo.setId(photoDto.getId());
        if(photoDto.getPhoto() != null && !photoDto.getPhoto().isEmpty()) {
            byte[] bytes = photoDto.getPhoto().getBytes();
            photo.setPhoto(bytes);
        }
        return photo;
    }

    // Metodo che permette la conversione da photo a photoDto
    private static PhotoDto convertPhotoToDto(Photo photo) {
        PhotoDto photoDto = new PhotoDto();
        photoDto.setTitle(photo.getTitle());
        photoDto.setDescription(photo.getDescription());
        photoDto.setVisibility(photo.isVisibility());
        photoDto.setCategories(photo.getCategories());
        photoDto.setId(photo.getId());
        return photoDto;
    }

    // Metodo che restituisce una photo in base all'id
    public PhotoDto getPhotoDtoById(Integer id) throws PhotoNotFoundException {

        Photo photo = getPhotoById(id);
        return convertPhotoToDto(photo);
    }

    // Metodo per modificare una foto con un id
    public Photo editPhoto(Photo photo) throws PhotoNotFoundException {
        Photo photoToEdit = getPhotoById(photo.getId());
        // sostituisco i valori dei campi previsti
        photoToEdit.setTitle(photo.getTitle());
        photoToEdit.setDescription(photo.getDescription());
        photoToEdit.setVisibility(photo.isVisibility());
        photoToEdit.setCategories(photo.getCategories());
        if (photo.getPhoto() != null && photo.getPhoto().length > 0) {
            photoToEdit.setPhoto(photo.getPhoto());
        }

        return photoRepository.save(photoToEdit);
    }

    public Photo editPhoto(PhotoDto photoDto) throws IOException {

        Photo photo = convertDtoToPhoto(photoDto);
        return editPhoto(photo);
    }

    // Metodo che elimina una foto da database

    public void deletePhoto(Integer id) {
        photoRepository.deleteById(id);
    }


    // Metodo per modificare la visibilità una foto con un id

    public Photo visibility(Photo photo) throws PhotoNotFoundException {
        Photo photoToEdit = getPhotoById(photo.getId());
        // sostituisco i valori dei campi previsti
        boolean visibility =photo.isVisibility();
        photoToEdit.setVisibility(!visibility);

        return photoRepository.save(photoToEdit);
    }
}
