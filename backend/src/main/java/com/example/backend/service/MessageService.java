package com.example.backend.service;

import com.example.backend.exceptions.CategoryNameUniqueException;
import com.example.backend.model.Category;
import com.example.backend.model.Message;
import com.example.backend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    // Metodo che recupera la lista dei messaggi ordinata per email
    public List<Message> getAll() {
        return messageRepository.findByOrderByEmail();
    }

}
