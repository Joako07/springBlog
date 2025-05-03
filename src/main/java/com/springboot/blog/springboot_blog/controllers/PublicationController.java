package com.springboot.blog.springboot_blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.springboot_blog.models.dtos.PublicationDto;
import com.springboot.blog.springboot_blog.services.PublicationService;
import com.springboot.blog.springboot_blog.utilities.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@Tag(name = "Publicaciones", description = "Endpoints de las publicaciones")
@RequestMapping("/api/publication")
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    @Operation(summary = "Obtener todas las publicaciones", description = "Recupera una lista paginada de todas las publicaciones",
             responses = {
            @ApiResponse(responseCode = "200", description = "Lista de publicaciones recuperada con éxito"),
            @ApiResponse(responseCode = "404", description = "Código de respuesta no documentado")})
    @GetMapping
    public ResponseEntity<Page<PublicationDto>> getPublications(
            @RequestParam(value = "pageNum", defaultValue = Constants.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = Constants.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int sizePage,
            @RequestParam(value = "sortBy", defaultValue = Constants.ORDENAR_POR_DEFECTO, required = false) String orderBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDirection) {
        return new ResponseEntity<>(publicationService.getAllPublications(pageNumber, sizePage, orderBy, sortDirection),
                HttpStatus.OK);
    }

    @Operation(summary = "Otener publicación por ID", description = "Recupera una publicación a travez del ID",
            responses = {
            @ApiResponse(responseCode = "200", description = "Publicaión recuperada con éxito"),
            @ApiResponse(responseCode = "404", description = "Publicación no encontrada")})
    @GetMapping("/{id}")
    public ResponseEntity<PublicationDto> getPublicationById(@PathVariable Long id) {
        return new ResponseEntity<>(publicationService.getById(id), HttpStatus.OK);
    }

    @Operation(summary = "Crear una publicación", description = "Crea una publicación nueva",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Recibe los datos de la publicación que deseas crear",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PublicationDto.class))),
            responses = {
            @ApiResponse(responseCode = "200", description = "Publicación creada con éxito"),
            @ApiResponse(responseCode = "400", description = "Publicación con errores en uno o varios campos"),
            @ApiResponse(responseCode = "404", description = "Código de respuesta no documentado")})
    @PostMapping
    public ResponseEntity<PublicationDto> createPublication(@Valid @RequestBody PublicationDto publicationDto) {
        return new ResponseEntity<>(publicationService.createPublication(publicationDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar una publicación", description = "Actualiza una publicación ya existente",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Recibe los datos que deseas actualizar",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PublicationDto.class))),
            responses = {
            @ApiResponse(responseCode = "200", description = "Publicación actualizada con éxito"),
            @ApiResponse(responseCode = "400", description = "Publicación con errores en uno o varios campos"),
            @ApiResponse(responseCode = "404", description = "Publicación no encontrada")})
    @PutMapping("/{id}")
    public ResponseEntity<PublicationDto> updatePublication(@Valid @RequestBody PublicationDto publicationDto,
            @PathVariable Long id) {
        return new ResponseEntity<>(publicationService.updatePublication(publicationDto, id), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar una publicación por ID", description = "Elimina una publicación ya existente a travez del ID",
            responses = {
            @ApiResponse(responseCode = "200", description = "Publicación eliminada con éxito"),
            @ApiResponse(responseCode = "404", description = "Publicación no encontrada")})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePublicationById(@PathVariable Long id) {
        publicationService.deletePublication(id);
        return new ResponseEntity<>("Publicación eliminada con exito", HttpStatus.OK);
    }

}
