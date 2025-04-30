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

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/publication")
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    
    @GetMapping
    public ResponseEntity<Page<PublicationDto>> getPublications(
            @RequestParam(value = "pageNum", defaultValue = Constants.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = Constants.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int sizePage,
            @RequestParam(value = "sortBy", defaultValue = Constants.ORDENAR_POR_DEFECTO, required = false) String orderBy,
            @RequestParam(value = "sortDir", defaultValue = "asc",required = false) String sortDirection){
        return new ResponseEntity<>(publicationService.getAllPublications(pageNumber, sizePage, orderBy,sortDirection),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicationDto> getPublicationById(@PathVariable Long id) {
        return new ResponseEntity<>(publicationService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PublicationDto> createPublication(@Valid @RequestBody PublicationDto publicationDto) {
        return new ResponseEntity<>(publicationService.createPublication(publicationDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicationDto> updatePublication(@Valid @RequestBody PublicationDto publicationDto,
            @PathVariable Long id) {
        return new ResponseEntity<>(publicationService.updatePublication(publicationDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePublicationById(@PathVariable Long id) {
        publicationService.deletePublication(id);
        return new ResponseEntity<>("Publicación eliminada con exito", HttpStatus.OK);
    }

}
