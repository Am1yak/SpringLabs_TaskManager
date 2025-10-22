package org.example.springlab2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Autowired
public class TaskController {
    @GetMapping("")
    public ResponseEntity<String> get() {

    }

    public
}
