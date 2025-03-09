package com.springboot.blog.springboot_blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.springboot_blog.models.dtos.CommentDto;
import com.springboot.blog.springboot_blog.services.CommentService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{publicationId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPublicationId(@PathVariable Long publicationId) {
        return new ResponseEntity<>(commentService.getAllCommentByPublicationId(publicationId), HttpStatus.OK);
    }
    
    @GetMapping("/{publicationId}/comment/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable long publicationId,@PathVariable long id) {
        return new ResponseEntity<>(commentService.getCommentById(publicationId, id),HttpStatus.OK);
    }
    
    @PostMapping("/{publicationId}/comment")
    public ResponseEntity<CommentDto> creatComment(@PathVariable long publicationId,@Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.creatComment(publicationId, commentDto),HttpStatus.CREATED);
    }

    @PutMapping("/{publicationId}/comment/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable long publicationId,@Valid @RequestBody CommentDto commentDto, @PathVariable long id) {
        return new ResponseEntity<>(commentService.updateComment(publicationId, commentDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{publicationId}/comment/{id}")
    public ResponseEntity<String> deletComment(@PathVariable long publicationId,@PathVariable long id){
        commentService.deletCommentById(publicationId, id);
        return new ResponseEntity<>("Comentario eliminado con exito", HttpStatus.OK);
    }
    
}
