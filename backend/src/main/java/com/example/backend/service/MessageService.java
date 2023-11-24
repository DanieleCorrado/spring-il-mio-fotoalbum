package com.example.backend.service;

import com.example.backend.exceptions.CategoryNameUniqueException;
import com.example.backend.model.Category;
import com.example.backend.model.Message;
import com.example.backend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    public Message save(Message message) {
        // trasformo il nome in lowercase
        // salvo su database
        return messageRepository.save(message);
    }
}
