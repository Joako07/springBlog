package com.springboot.blog.springboot_blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.springboot_blog.models.dtos.CommentDto;
import com.springboot.blog.springboot_blog.services.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@Tag(name = "Comentarios", description = "Endpoints de los comentarios")
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Operation(summary = "Obtener todos los comentarios por ID de publicación", description = "Recupera una lista de todos los comentarios de una publicación a travez de su ID",
            responses = {
            @ApiResponse(responseCode = "200", description = "Lista de comentarios recuperada con éxito"),
            @ApiResponse(responseCode = "404", description = "Publicación no encontrada")})
    @GetMapping("/{publicationId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPublicationId(@PathVariable Long publicationId) {
        return new ResponseEntity<>(commentService.getAllCommentByPublicationId(publicationId), HttpStatus.OK);
    }

    @Operation(summary = "Obtener un comentario de una publicación a travez de los IDS", description = "Recupera un comentario de una publicación a travez de los IDS",
            responses = {
            @ApiResponse(responseCode = "200", description = "Comentario recuperado con éxito"),
            @ApiResponse(responseCode = "404", description = "Publicación o comentario no encontrados")})
    @GetMapping("/{publicationId}/comment/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable long publicationId, @PathVariable long id) {
        return new ResponseEntity<>(commentService.getCommentById(publicationId, id), HttpStatus.OK);
    }

    @Operation(summary = "Crear un comentario", description = "Crea un comentario nuevo",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Recibe los datos del comentario que deseas crear",
            required = true, 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))),
            responses = {
            @ApiResponse(responseCode = "200", description = "Comentario creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Comentario con errores en uno o varios campos"),
            @ApiResponse(responseCode = "404", description = "Publicación no encontrada")})
    @PostMapping("/{publicationId}/comment")
    public ResponseEntity<CommentDto> createComment(@PathVariable long publicationId,
            @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(publicationId, commentDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un comentario de una publicación a travez de los IDS", description = "Actualiza un comentario ya existente en una publicación a travez de los IDS", 
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Recibe los datos que deseas actualizar", 
            required = true, 
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))),
            responses = {
            @ApiResponse(responseCode = "200", description = "Comentario actualizado con éxito"),
            @ApiResponse(responseCode = "400", description = "Comentario con errores en uno o varios campos"),
            @ApiResponse(responseCode = "404", description = "Publicación o comentario no encontrados")})
    @PutMapping("/{publicationId}/comment/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable long publicationId,
            @Valid @RequestBody CommentDto commentDto, @PathVariable long id) {
        return new ResponseEntity<>(commentService.updateComment(publicationId, commentDto, id), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar un comentario de una publicación a travez de los IDS", description = "Elimina un comentario ya existente en una publicación a travez del IDS",
            responses = {
            @ApiResponse(responseCode = "200", description = "Comentario eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "Publicación o comentario no encontrados")})
    @DeleteMapping("/{publicationId}/comment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable long publicationId, @PathVariable long id) {
        commentService.deleteCommentById(publicationId, id);
        return new ResponseEntity<>("Comentario eliminado con exito", HttpStatus.OK);
    }

}
