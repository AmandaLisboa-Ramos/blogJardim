package com.jardim.blogJardim.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jardim.blogJardim.repository.ComentarioRepository;

@RestController
@RequestMapping("/comentarios")
@CrossOrigin(origins = "*")
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @DeleteMapping("/{id}")
    public void deletarComentario(@PathVariable Long id) {
        if (comentarioRepository.existsById(id)) {
            comentarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("Comentário não encontrado.");
        }
    }
}