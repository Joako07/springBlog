package com.springboot.blog.springboot_blog.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.springboot.blog.springboot_blog.DataProvider;
import com.springboot.blog.springboot_blog.exceptions.BlogAppException;
import com.springboot.blog.springboot_blog.exceptions.ResourceNotFoundException;
import com.springboot.blog.springboot_blog.models.dtos.CommentDto;
import com.springboot.blog.springboot_blog.models.entities.CommentEntity;
import com.springboot.blog.springboot_blog.models.entities.PublicationEntity;
import com.springboot.blog.springboot_blog.models.mappers.CommentMapper;
import com.springboot.blog.springboot_blog.repositories.CommentRepository;
import com.springboot.blog.springboot_blog.repositories.PublicationRepository;

//Esta anotación nos permite usar mocks (objetos simulados)
@ExtendWith(MockitoExtension.class)
public class CommentServiceImpTest {

    // creo un mock (objeto simulado) de CommentRepository y PublicationRepository
    // "LAS DEPENDENCIAS"
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PublicationRepository publicationRepository;

    // Creo una instancia de PublicationServiceImp "LA CLASE A TESTEAR"
    @InjectMocks
    private CommentServiceImp commentServiceImp;

    @DisplayName("Test para crear comentario")
    @Test
    public void testCreateComment() {
        // Given
        Long publicationId = 1L;
        PublicationEntity publicationEntity = DataProvider.newPublicationEntity();
        CommentEntity commentEntity = DataProvider.newCommentEntity(publicationEntity);
        CommentDto commentDto = DataProvider.newCommentDto();

        // When
        try (MockedStatic<CommentMapper> mapperMock = mockStatic(CommentMapper.class)) {
            mapperMock.when(() -> CommentMapper.entityToDto(commentEntity)).thenReturn(commentDto);
            mapperMock.when(() -> CommentMapper.dtoToEntity(commentDto)).thenReturn(commentEntity);
            when(publicationRepository.findById(publicationId)).thenReturn(Optional.of(publicationEntity));
            when(commentRepository.save(any(CommentEntity.class))).thenReturn(commentEntity);

            CommentDto result = commentServiceImp.createComment(publicationId, commentDto);

            // then
            assertNotNull(result);
            assertEquals(commentDto.getName(), result.getName());
            assertEquals(commentDto.getEmail(), result.getEmail());
            assertEquals(commentDto.getBody(), result.getBody());
            verify(publicationRepository).findById(publicationId);
            verify(commentRepository).save(any(CommentEntity.class));
        }
    }

    @DisplayName("Test para publicación no encontrada en CreateComment")
    @Test
    public void testCreateCommentError() {

        // Given
        Long id = 123L;
        PublicationEntity publicationEntity = DataProvider.newPublicationEntity();
        CommentEntity commentEntity = DataProvider.newCommentEntity(publicationEntity);
        CommentDto commentDto = DataProvider.newCommentDto();

        // When
        try (MockedStatic<CommentMapper> mapperMock = mockStatic(CommentMapper.class)) {
            mapperMock.when(() -> CommentMapper.entityToDto(commentEntity)).thenReturn(commentDto);
            mapperMock.when(() -> CommentMapper.dtoToEntity(commentDto)).thenReturn(commentEntity);

            when(publicationRepository.findById(id)).thenReturn(Optional.empty());

            ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
                commentServiceImp.createComment(id, commentDto);
            });

            // Then
            assertEquals("publicacion", exception.getNombreDelRecurso());
            assertEquals("id", exception.getNombreDelCampo());
            assertEquals(id, exception.getValorDelCampo());
            assertEquals("Publicación no encontrada", exception.getMessage());
            verify(publicationRepository).findById(id);
        }

    }

    @DisplayName("Test para obtener listado de commentarios")
    @Test
    public void testGetCommentById() {

        // Given
        Long id = 1L;
        PublicationEntity publicationEntity = DataProvider.newPublicationEntity();
        CommentEntity commentEntity = DataProvider.newCommentEntity(publicationEntity);
        CommentDto commentDto = DataProvider.newCommentDto();

        // When
        try (MockedStatic<CommentMapper> mapperMock = mockStatic(CommentMapper.class)) {
            mapperMock.when(() -> CommentMapper.entityToDto(commentEntity)).thenReturn(commentDto);
            when(publicationRepository.findById(id)).thenReturn(Optional.of(publicationEntity));
            when(commentRepository.findById(id)).thenReturn(Optional.of(commentEntity));

            CommentDto result = commentServiceImp.getCommentById(id, id);

            // Then
            assertNotNull(result);
            assertEquals(commentDto.getName(), result.getName());
            assertEquals(commentDto.getEmail(), result.getEmail());
            assertEquals(commentDto.getBody(), result.getBody());
            verify(publicationRepository).findById(id);
            verify(commentRepository).findById(id);
        }
    }

    @DisplayName("Test para publicación no encontrada en GetCommentById")
    @Test
    public void testGetCommentByIdError1() {

        // Given
        Long publicationId = 123L, commentId = 1L;

        // When
        when(publicationRepository.findById(publicationId)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            commentServiceImp.getCommentById(publicationId, commentId);
        });

        // Then
        assertEquals("publicacion", exception.getNombreDelRecurso());
        assertEquals("id", exception.getNombreDelCampo());
        assertEquals(publicationId, exception.getValorDelCampo());
        assertEquals("Publicación no encontrada", exception.getMessage());
        verify(publicationRepository).findById(publicationId);
    }

    @DisplayName("Test para comentario no encontrada en GetCommentById")
    @Test
    public void testGetCommentByIdError2() {

        // Given
        Long publicationId = 1L, commentId = 123L;
        PublicationEntity publicationEntity = DataProvider.newPublicationEntity();

        // When
        when(publicationRepository.findById(publicationId)).thenReturn(Optional.of(publicationEntity));
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            commentServiceImp.getCommentById(publicationId, commentId);
        });

        // Then
        assertEquals("comentario", exception.getNombreDelRecurso());
        assertEquals("id", exception.getNombreDelCampo());
        assertEquals(commentId, exception.getValorDelCampo());
        assertEquals("El comentario no existe", exception.getMessage());
        verify(publicationRepository).findById(publicationId);
        verify(commentRepository).findById(commentId);

    }

    @DisplayName("Test para ids diferentes en GetCommentById")
    @Test
    public void testGetCommentByIdError3() {

        // Given
        Long id = 1L, commentPublicationId = 2L;
        PublicationEntity publicationEntity = DataProvider.newPublicationEntity();
        PublicationEntity otherPublication = DataProvider.newPublicationEntity();
        otherPublication.setId(commentPublicationId);
        CommentEntity commentEntity = DataProvider.newCommentEntity(otherPublication);

        // When
        when(publicationRepository.findById(id)).thenReturn(Optional.of(publicationEntity));
        when(commentRepository.findById(id)).thenReturn(Optional.of(commentEntity));

        BlogAppException exception = assertThrows(BlogAppException.class, () -> {
            commentServiceImp.getCommentById(id, id);
        });

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("El comentario no pertenece a la publicación ", exception.getMessage());
        verify(publicationRepository).findById(id);
        verify(commentRepository).findById(id);
    }

    @DisplayName("Test para actualizar comentario")
    @Test
    public void testUpdateComment() {

        // Given
        Long id = 1L;
        PublicationEntity publicationEntity = DataProvider.newPublicationEntity();
        CommentEntity currentCommentEntity = DataProvider.newCommentEntity(publicationEntity);
        CommentEntity updateCommentEntity = DataProvider.newUpdateCommentEntity(publicationEntity);
        CommentDto updateCommentDto = DataProvider.newUpdateCommentDto();

        // When
        try (MockedStatic<CommentMapper> mapperMock = mockStatic(CommentMapper.class)) {
            mapperMock.when(() -> CommentMapper.entityToDto(updateCommentEntity)).thenReturn(updateCommentDto);
            when(publicationRepository.findById(id)).thenReturn(Optional.of(publicationEntity));
            when(commentRepository.findById(id)).thenReturn(Optional.of(currentCommentEntity));
            when(commentRepository.save(any(CommentEntity.class))).thenReturn(updateCommentEntity);

            CommentDto result = commentServiceImp.updateComment(id, updateCommentDto, id);

            // Then
            assertNotNull(result);
            assertEquals(updateCommentDto.getName(), result.getName());
            assertEquals(updateCommentDto.getEmail(), result.getEmail());
            assertEquals(updateCommentDto.getBody(), result.getBody());
            verify(publicationRepository).findById(id);
            verify(commentRepository).findById(id);
            verify(commentRepository).save(any(CommentEntity.class));
        }
    }

    @DisplayName("Test para publicación no encontrada en UpdateComment")
    @Test
    public void testUpdateCommentError1() {

        // Given
        Long publicationId = 123L, commentId = 1L;
        CommentDto commentDto = DataProvider.newUpdateCommentDto();

        // When
        when(publicationRepository.findById(publicationId)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            commentServiceImp.updateComment(publicationId, commentDto, commentId);
        });

        // Then
        assertEquals("publicacion", exception.getNombreDelRecurso());
        assertEquals("id", exception.getNombreDelCampo());
        assertEquals(publicationId, exception.getValorDelCampo());
        assertEquals("Publicación no encontrada", exception.getMessage());
        verify(publicationRepository).findById(publicationId);
    }

    @DisplayName("Test para comentario no encontrada en UpdateComment")
    @Test
    public void testUpdateCommentError2() {

        // Given
        Long publicationId = 1L, commentId = 123L;
        PublicationEntity publicationEntity = DataProvider.newPublicationEntity();
        CommentDto commentDto = DataProvider.newUpdateCommentDto();

        // When
        when(publicationRepository.findById(publicationId)).thenReturn(Optional.of(publicationEntity));
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            commentServiceImp.updateComment(publicationId, commentDto, commentId);
        });

        // Then
        assertEquals("comentario", exception.getNombreDelRecurso());
        assertEquals("id", exception.getNombreDelCampo());
        assertEquals(commentId, exception.getValorDelCampo());
        assertEquals("El comentario no existe", exception.getMessage());
        verify(publicationRepository).findById(publicationId);
        verify(commentRepository).findById(commentId);

    }

    @DisplayName("Test para ids diferentes en  UpdateComment")
    @Test
    public void testUpdateCommentError3() {

        // Given
        Long id = 1L, commentPublicationId = 2L;
        PublicationEntity publicationEntity = DataProvider.newPublicationEntity();
        PublicationEntity otherPublication = DataProvider.newPublicationEntity();
        otherPublication.setId(commentPublicationId);
        CommentEntity commentEntity = DataProvider.newCommentEntity(otherPublication);
        CommentDto commentDto = DataProvider.newUpdateCommentDto();

        // When
        when(publicationRepository.findById(id)).thenReturn(Optional.of(publicationEntity));
        when(commentRepository.findById(id)).thenReturn(Optional.of(commentEntity));

        BlogAppException exception = assertThrows(BlogAppException.class, () -> {
            commentServiceImp.updateComment(id, commentDto, id);
        });

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("El comentario no pertenece a la publicación ", exception.getMessage());
        verify(publicationRepository).findById(id);
        verify(commentRepository).findById(id);
    }

    @DisplayName("Test para borrar comentario")
    @Test
    public void testDeleteCommentById() {

        // Given
        Long id = 1L;
        PublicationEntity publicationEntity = DataProvider.newPublicationEntity();
        CommentEntity commentEntity = DataProvider.newCommentEntity(publicationEntity);
        commentEntity.getPublication().setComments(null);

        // When
        when(publicationRepository.findById(id)).thenReturn(Optional.of(publicationEntity));
        when(commentRepository.findById(id)).thenReturn(Optional.of(commentEntity));

        commentServiceImp.deleteCommentById(id, id);

        // Then
        ArgumentCaptor<CommentEntity> commentArgument = ArgumentCaptor.forClass(CommentEntity.class);
        verify(commentRepository).delete(commentArgument.capture());
        verify(publicationRepository).findById(id);
        verify(commentRepository).findById(id);
    }

    @DisplayName("Test para publicación no encontrada en DeleteCommentById")
    @Test
    public void testDeleteCommentByIdError1() {

        // Given
        Long publicationId = 123L, commentId = 1L;

        // When
        when(publicationRepository.findById(publicationId)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            commentServiceImp.deleteCommentById(publicationId, commentId);
        });

        // Then
        assertEquals("publicacion", exception.getNombreDelRecurso());
        assertEquals("id", exception.getNombreDelCampo());
        assertEquals(publicationId, exception.getValorDelCampo());
        assertEquals("Publicación no encontrada", exception.getMessage());
        verify(publicationRepository).findById(publicationId);
    }

    @DisplayName("Test para comentario no encontrada en  DeleteCommentById")
    @Test
    public void testDeleteCommentByIdError2() {

        // Given
        Long publicationId = 1L, commentId = 123L;
        PublicationEntity publicationEntity = DataProvider.newPublicationEntity();

        // When
        when(publicationRepository.findById(publicationId)).thenReturn(Optional.of(publicationEntity));
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            commentServiceImp.deleteCommentById(publicationId, commentId);
        });

        // Then
        assertEquals("comentario", exception.getNombreDelRecurso());
        assertEquals("id", exception.getNombreDelCampo());
        assertEquals(commentId, exception.getValorDelCampo());
        assertEquals("El comentario no existe", exception.getMessage());
        verify(publicationRepository).findById(publicationId);
        verify(commentRepository).findById(commentId);

    }

    @DisplayName("Test para ids diferentes en  DeleteCommentById")
    @Test
    public void testDeleteCommentByIdError3() {

        // Given
        Long id = 1L, commentPublicationId = 2L;
        PublicationEntity publicationEntity = DataProvider.newPublicationEntity();
        PublicationEntity otherPublication = DataProvider.newPublicationEntity();
        otherPublication.setId(commentPublicationId);
        CommentEntity commentEntity = DataProvider.newCommentEntity(otherPublication);
        // CommentDto commentDto = DataProvider.newUpdateCommentDto();

        // When
        when(publicationRepository.findById(id)).thenReturn(Optional.of(publicationEntity));
        when(commentRepository.findById(id)).thenReturn(Optional.of(commentEntity));

        BlogAppException exception = assertThrows(BlogAppException.class, () -> {
            commentServiceImp.deleteCommentById(id, id);
        });

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("El comentario no pertenece a la publicación ", exception.getMessage());
        verify(publicationRepository).findById(id);
        verify(commentRepository).findById(id);
    }

    @DisplayName("Test para obtener todos los comentarios por id de publicación")
    @Test
    public void testGetAllCommentByPublicationId() {
        // Given
        Long publicationId = 1L;
        PublicationEntity publicationEntity = DataProvider.newPublicationEntity();
        List<CommentEntity> commentsEntities = DataProvider.listSampleComments2(publicationEntity);
        List<CommentDto> commentsDtos = DataProvider.listSampleCommentsDtos();

        // when
        try (MockedStatic<CommentMapper> mapperMock = mockStatic(CommentMapper.class)) {
            for (int i = 0; i < commentsEntities.size(); i++) {
                final int index = i; // La variable i no puede cambiar dentro de una funcion lambda por eso creo un final.
                mapperMock.when(() -> CommentMapper.entityToDto(commentsEntities.get(index)))
                        .thenReturn(commentsDtos.get(index));
            }

            when(commentRepository.findByPublicationId(publicationId)).thenReturn(commentsEntities);

            List<CommentDto> result = commentServiceImp.getAllCommentByPublicationId(publicationId);

            // Then
            assertNotNull(result);
            assertFalse(result.isEmpty());
            assertEquals(2, result.size());
            assertEquals(commentsDtos.get(0).getName(), result.get(0).getName());
            assertEquals(commentsDtos.get(0).getEmail(), result.get(0).getEmail());
            assertEquals(commentsDtos.get(0).getBody(), result.get(0).getBody());
            verify(commentRepository).findByPublicationId(publicationId);
        }
    }
}
