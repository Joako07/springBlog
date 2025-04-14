package com.springboot.blog.springboot_blog.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.springboot.blog.springboot_blog.DataProvider;
import com.springboot.blog.springboot_blog.models.dtos.PublicationDto;
import com.springboot.blog.springboot_blog.models.entities.PublicationEntity;
import com.springboot.blog.springboot_blog.repositories.PublicationRepository;

//Esta anotación nos permite usar mocks (objetos simulados)
@ExtendWith(MockitoExtension.class)
public class PublicationServiceImpTest {

    // creo un mock (objeto simulado) de PublicationRepository "LA DEPENDENCIA"
    @Mock
    private PublicationRepository publicationRepository;

    // Creo una instancia de PublicationServiceImp "LA CLASE A TESTEAR"
    @InjectMocks
    private PublicationServiceImp publicationServiceImp;

    @DisplayName("Testa para obtener listado de publicaciónes")
    @Test
    public void testGetAllPublications() {
        // When
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "title"));
        Page<PublicationEntity> publicationPage = new PageImpl<>(DataProvider.listSamplePublications());
        when(publicationRepository.findAll(pageable)).thenReturn(publicationPage);

        Page<PublicationDto> result = publicationServiceImp.getAllPublications(0, 5, "title", "desc");

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.getSize());
    }

    @DisplayName("Testa para guardar una publicación")
    @Test
    public void testCrearPublication(){
        //When
        when(publicationRepository.save(DataProvider.newPublicationEntity())).thenReturn(DataProvider.newPublicationEntity());
        PublicationDto result = publicationServiceImp.createPublication(DataProvider.newPublicationDto());

        //Then
        assertNotNull(result);
    }
}
