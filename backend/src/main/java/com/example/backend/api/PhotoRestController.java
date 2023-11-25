package com.example.backend.api;

import com.example.backend.model.Message;
import com.example.backend.model.Photo;
import com.example.backend.repository.MessageRepository;
import com.example.backend.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/photos")
@CrossOrigin
public class PhotoRestController {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private MessageRepository messageRepository;

    // Endpoint per mostrare la lista di tutte le foto
    @GetMapping
    public List<Photo> index(@RequestParam Optional<String> search) {
        return photoService.getVisiblePhotoList(search);
    }


    // Endpoint che recupera il file della singola foto
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> photoFile(@PathVariable Integer id) {
        Photo photo = photoService.getPhotoById(id);
        byte[] photobytes = photo.getPhoto();
        if (photobytes != null && photobytes.length > 0) {
            MediaType mediaType = MediaType.IMAGE_JPEG;

            return ResponseEntity.ok().contentType(mediaType).body(photobytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endopoint che permette il salvataggio di un messaggio in database
    @PostMapping
    public ResponseEntity<Message> saveMessage(@RequestBody Message message) {
        messageRepository.save(message);

        // Creare una risposta API
        return ResponseEntity.ok(message);
    }
}
