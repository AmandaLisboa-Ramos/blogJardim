package com.jardim.blogJardim.controller;

import com.jardim.blogJardim.model.Comentario;
import com.jardim.blogJardim.model.Post;
import com.jardim.blogJardim.repository.ComentarioRepository;
import com.jardim.blogJardim.repository.PostRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*")
public class PostController {

    private final PostRepository postRepository;
    private final ComentarioRepository comentarioRepository;

    public PostController(PostRepository postRepository, ComentarioRepository comentarioRepository) {
        this.postRepository = postRepository;
        this.comentarioRepository = comentarioRepository;
    }

    // Listar todos os posts
    @GetMapping
    public List<Post> listarTodos() {
        return postRepository.findAll();
    }

    // Buscar post por ID
    @GetMapping("/{id}")
    public ResponseEntity<Post> buscarPorId(@PathVariable Long id) {
        return postRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar novo post
    @PostMapping
    public Post criar(@RequestBody Post post) {
        return postRepository.save(post);
    }

    // Editar post existente
    @PutMapping("/{id}")
    public ResponseEntity<Post> atualizar(@PathVariable Long id, @RequestBody Post postAtualizado) {
        return postRepository.findById(id).map(post -> {
            post.setTitulo(postAtualizado.getTitulo());
            post.setConteudo(postAtualizado.getConteudo());
            post.setAutor(postAtualizado.getAutor());
            post.setEmail(postAtualizado.getEmail());
            return ResponseEntity.ok(postRepository.save(post));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Deletar post
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!postRepository.existsById(id)) return ResponseEntity.notFound().build();
        postRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Adicionar comentário a um post
    @PostMapping("/{id}/comentarios")
    public ResponseEntity<Comentario> adicionarComentario(
            @PathVariable Long id, @RequestBody Comentario comentario) {

        return postRepository.findById(id).map(post -> {

            comentario.setPost(post);
            post.getComentarios().add(comentario); // importante!

            comentarioRepository.save(comentario);
            postRepository.save(post);

            return ResponseEntity.ok(comentario);
        }).orElse(ResponseEntity.notFound().build());
    }

    
    @GetMapping("/{id}/comentarios")
    public ResponseEntity<List<Comentario>> listarComentarios(@PathVariable Long id) {
        return postRepository.findById(id)
                .map(post -> ResponseEntity.ok(post.getComentarios()))
                .orElse(ResponseEntity.notFound().build());
    }

    // Dar "amei" a um post
    @PostMapping("/{id}/amei")
    public ResponseEntity<Post> darAmei(@PathVariable Long id) {
        return postRepository.findById(id).map(post -> {
            post.setQuantidadeAmeis(post.getQuantidadeAmeis() + 1);
            return ResponseEntity.ok(postRepository.save(post));
        }).orElse(ResponseEntity.notFound().build());
    }
}
