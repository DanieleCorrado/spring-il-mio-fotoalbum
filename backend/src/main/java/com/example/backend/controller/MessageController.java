package com.example.backend.controller;

import com.example.backend.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    MessageService messageService;

    // Metodo che recupera la lista dei messaggi dal database
    @GetMapping
    public String index(Model model) {

        model.addAttribute("messageList", messageService.getAll());

        return "messages/index";
    }
}
