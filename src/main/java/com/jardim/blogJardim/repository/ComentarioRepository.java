package com.jardim.blogJardim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jardim.blogJardim.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {}
