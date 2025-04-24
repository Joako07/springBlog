package com.springboot.blog.springboot_blog.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.springboot.blog.springboot_blog.DataProvider;
import com.springboot.blog.springboot_blog.exceptions.ResourceNotFoundException;
import com.springboot.blog.springboot_blog.models.dtos.PublicationDto;
import com.springboot.blog.springboot_blog.models.entities.PublicationEntity;
import com.springboot.blog.springboot_blog.models.mappers.PublicationMapper;
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

        // Given
        List<PublicationEntity> publicationEntities = DataProvider.listSamplePublications();

        List<PublicationDto> publicationDtos = publicationEntities.stream()
                .map(PublicationMapper::entityToDto)
                .collect(Collectors.toList());

        // When
        try (MockedStatic<PublicationMapper> mapperMock = mockStatic(PublicationMapper.class)) {
            for (int i = 0; i < publicationEntities.size(); i++) {
                final int index = i; // La variable i no puede cambiar dentro de una funcion lambda por eso creo un
                                     // final.
                mapperMock.when(() -> PublicationMapper.entityToDto(publicationEntities.get(index)))
                        .thenReturn(publicationDtos.get(index));
            }
            Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "title"));
            Page<PublicationEntity> publicationPage = new PageImpl<>(publicationEntities);
            when(publicationRepository.findAll(pageable)).thenReturn(publicationPage);

            Page<PublicationDto> result = publicationServiceImp.getAllPublications(0, 5, "title", "desc");

            // Then
            assertNotNull(result);
            assertFalse(result.isEmpty());
            assertEquals(2, result.getSize());
        }
    }

    @DisplayName("Testa para guardar una publicación")
    @Test
    public void testCreatePublication() {

        // Given
        PublicationDto publicationDto = PublicationMapper.entityToDto(DataProvider.newPublicationEntity());
        PublicationEntity publicationEntity = DataProvider.newPublicationEntity();

        // When
        try (MockedStatic<PublicationMapper> mapperMock = mockStatic(PublicationMapper.class)) {
            mapperMock.when(() -> PublicationMapper.dtoToEntity(publicationDto)).thenReturn(publicationEntity);
            mapperMock.when(() -> PublicationMapper.entityToDto(publicationEntity)).thenReturn(publicationDto);
            when(publicationRepository.save(any(PublicationEntity.class))).thenReturn(publicationEntity);

            PublicationDto result = publicationServiceImp.createPublication(publicationDto);

            // Then
            assertNotNull(result);
            assertEquals(publicationDto.getTitle(), result.getTitle());
            assertEquals(publicationDto.getDescription(), result.getDescription());
            assertEquals(publicationDto.getContent(), result.getContent());
            assertEquals(publicationDto.getComments(), result.getComments());
            verify(publicationRepository).save(publicationEntity);
        }
    }

    @DisplayName("Test para obtener una publicación")
    @Test
    public void testGetById() {
        // Given
        Long id = 1L;
        PublicationDto publicationDto = PublicationMapper.entityToDto(DataProvider.newPublicationEntity());
        PublicationEntity publicationEntity = DataProvider.newPublicationEntity();

        // when
        try (MockedStatic<PublicationMapper> mapperMock = mockStatic(PublicationMapper.class)) {
            mapperMock.when(() -> PublicationMapper.entityToDto(publicationEntity)).thenReturn(publicationDto);
            when(publicationRepository.findById(id)).thenReturn(Optional.of(publicationEntity));

        }

        PublicationDto result = publicationServiceImp.getById(id);

        // Then
        assertNotNull(result);
        assertEquals(publicationDto.getTitle(), result.getTitle());
        assertEquals(publicationDto.getDescription(), result.getDescription());
        assertEquals(publicationDto.getContent(), result.getContent());
        assertEquals(publicationDto.getComments(), result.getComments());
        verify(publicationRepository).findById(id);
    }

    @DisplayName("Test para publicación no encontrada en getById")
    @Test
    public void testGetByIdError() {

        // Given
        long id = 123L;

        // When
        when(publicationRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            publicationServiceImp.getById(id);
        });

        // Then
        assertEquals("publicacion", exception.getNombreDelRecurso());
        assertEquals("id", exception.getNombreDelCampo());
        assertEquals(id, exception.getValorDelCampo());
        assertEquals("Publicación no encontrada", exception.getMessage());
        verify(publicationRepository).findById(id);
    }

    @DisplayName("Testa para actualizar publicación")
    @Test
    public void testUpdatePublication() {

        // Given
        Long id = 1L;
        PublicationEntity currentPublicationEntity = DataProvider.newPublicationEntity();
        PublicationEntity updatedPublicationEntity = DataProvider.updatedPublicationEntity();
        PublicationDto updatePublicationDto = PublicationMapper.entityToDto(updatedPublicationEntity);

        // When
        try (MockedStatic<PublicationMapper> mapperMock = mockStatic(PublicationMapper.class)) {
            when(publicationRepository.findById(id)).thenReturn(Optional.of(currentPublicationEntity));
            when(publicationRepository.save(any(PublicationEntity.class))).thenReturn(updatedPublicationEntity);
            mapperMock.when(() -> PublicationMapper.entityToDto(updatedPublicationEntity))
                    .thenReturn(updatePublicationDto);

        }

        PublicationDto result = publicationServiceImp.updatePublication(updatePublicationDto, id);

        // Then
        assertNotNull(result);
        assertEquals(updatePublicationDto.getTitle(), result.getTitle());
        assertEquals(updatePublicationDto.getDescription(), result.getDescription());
        assertEquals(updatePublicationDto.getContent(), result.getContent());
        assertEquals(updatePublicationDto.getComments(), result.getComments());
        verify(publicationRepository).findById(id);
        verify(publicationRepository).save(any(PublicationEntity.class));
    }

    @DisplayName("Test para publicación no encontrada en update")
    @Test
    public void testUpdatePublicationError() {
        // Given
        Long id = 123L;
        PublicationDto publicationDto = PublicationMapper.entityToDto(DataProvider.newPublicationEntity());

        // When
        when(publicationRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            publicationServiceImp.updatePublication(publicationDto, id);
        });

        // Then
        assertEquals("publicacion", exception.getNombreDelRecurso());
        assertEquals("id", exception.getNombreDelCampo());
        assertEquals(id, exception.getValorDelCampo());
        assertEquals("Publicación no encontrada", exception.getMessage());
        verify(publicationRepository).findById(id);
    }

    @DisplayName("Test para eliminar publicacion ")
    @Test
    public void testDeletPublication(){
        //Given
        Long id = 1L;
        PublicationEntity publicationEntity = DataProvider.newPublicationEntity();

        //When
       when(publicationRepository.findById(id)).thenReturn(Optional.of(publicationEntity));
       publicationServiceImp.deletPublication(id);

        //Then
        ArgumentCaptor<Long> longArgument = ArgumentCaptor.forClass(Long.class);
        verify(publicationRepository).deleteById(longArgument.capture());
       verify(publicationRepository).findById(id);
       verify(publicationRepository).deleteById(id);
    }

    @DisplayName("Test para publicación no encontrada en delete")
    @Test
    public void testDeletPublicationError(){
        //Given
        Long id = 123L;

        //When
        when(publicationRepository.findById(id)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            publicationServiceImp.deletPublication(id); 
        });
        
        //Then
        assertEquals("publicacion", exception.getNombreDelRecurso());
        assertEquals("id", exception.getNombreDelCampo());
        assertEquals(id, exception.getValorDelCampo());
        assertEquals("Publicación no encontrada", exception.getMessage());
        verify(publicationRepository).findById(id);
        verify(publicationRepository, never()).deleteById(anyLong()); //Verifico que no haya ejecutado el metodo. Es decri que no se borro ninguna publicación 

    }

}
