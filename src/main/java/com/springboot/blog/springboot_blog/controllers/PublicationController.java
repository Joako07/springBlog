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
            @RequestParam(value = "PageNum", defaultValue = Constants.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int pageNumber,
            @RequestParam(value = "PageSize",defaultValue = Constants.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int sizePage,
            @RequestParam(value = "sortBy", defaultValue = Constants.ORDENAR_POR_DEFECTO, required = false) String orderBy,
            @RequestParam(value = "sortDir", defaultValue = "ace",required = false) String sortDirection){
        return new ResponseEntity<>(publicationService.getAllPublications(pageNumber, sizePage, orderBy,sortDirection),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicationDto> getPublicationById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(publicationService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PublicationDto> createPublication(@RequestBody PublicationDto publicationDto) {
        return new ResponseEntity<>(publicationService.crearPublication(publicationDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicationDto> updatePublication(@RequestBody PublicationDto publicationDto,
            @PathVariable("id") Long id) {
        return new ResponseEntity<>(publicationService.updatePublication(publicationDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletPublicationById(@PathVariable("id") Long id) {
        publicationService.deletPublication(id);
        return new ResponseEntity<>("Publicación eliminada con exito", HttpStatus.OK);
    }

}
