package com.springboot.blog.springboot_blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.springboot_blog.models.dtos.ComentDto;
import com.springboot.blog.springboot_blog.services.ComentService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/coment/")
public class ComentController {

    @Autowired
    private ComentService comentService;

    @GetMapping("/{publicationId}/coments")
    public ResponseEntity<List<ComentDto>> getComentsByPublicationId(@PathVariable Long publicationId) {
        return new ResponseEntity<>(comentService.getAllComentByPublicationId(publicationId), HttpStatus.OK);
    }
    
    @GetMapping("/{publicationId}/coment/{id}")
    public ResponseEntity<ComentDto> getComentById(@PathVariable long publicationId,@PathVariable long id) {
        return new ResponseEntity<>(comentService.getComentById(publicationId, id),HttpStatus.OK);
    }
    
    @PostMapping("/{publicationId}/coment")
    public ResponseEntity<ComentDto> creatComent(@PathVariable long publicationId,@Valid @RequestBody ComentDto comentDto) {
        return new ResponseEntity<>(comentService.creatComent(publicationId, comentDto),HttpStatus.CREATED);
    }

    @PutMapping("/{publicationId}/coment/{id}")
    public ResponseEntity<ComentDto> updateComent(@PathVariable long publicationId,@Valid @RequestBody ComentDto comentDto, @PathVariable long id) {
        return new ResponseEntity<>(comentService.updateComent(publicationId, comentDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{publicationId}/coment/{id}")
    public ResponseEntity<String> deletComent(@PathVariable long publicationId,@PathVariable long id){
        comentService.deletComentById(publicationId, id);
        return new ResponseEntity<>("Comentario eliminado con exito", HttpStatus.OK);
    }
    
}
