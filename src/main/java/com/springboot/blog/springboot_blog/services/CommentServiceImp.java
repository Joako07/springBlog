package com.springboot.blog.springboot_blog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.blog.springboot_blog.exceptions.BlogAppException;
import com.springboot.blog.springboot_blog.exceptions.ResourceNotFoundException;
import com.springboot.blog.springboot_blog.models.dtos.CommentDto;
import com.springboot.blog.springboot_blog.models.entities.CommentEntity;
import com.springboot.blog.springboot_blog.models.entities.PublicationEntity;
import com.springboot.blog.springboot_blog.models.mappers.CommentMapper;
import com.springboot.blog.springboot_blog.repositories.CommentRepository;
import com.springboot.blog.springboot_blog.repositories.PublicationRepository;

@Service
public class CommentServiceImp implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PublicationRepository publicationRepository;

    @Override
    @Transactional
    public CommentDto creatComment(long publicationId, CommentDto commentDto) {
         //Convierto de dto a entidad para crear el comentario 
        CommentEntity commentEntity = CommentMapper.dtoToEntity(commentDto);

        //Verifico que exista le publicación
          Optional<PublicationEntity> publication = publicationRepository.findById(publicationId);
         if(publication.isEmpty()){
            throw ResourceNotFoundException.builder()
            .nombreDelRecurso("publicacion")
            .nombreDelCampo("id")
            .valorDelCampo(publicationId)
            .message("Publicación no encontrada")
            .build();
         }

         //Le asigno la publicación y lo guardo
         commentEntity.setPublication(publication.get());
         CommentEntity commentEntity2 = commentRepository.save(commentEntity);

         //Convierto de entidad a dto para mostrar la respuesta
        return CommentMapper.entityToDto(commentEntity2);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentDto> getAllComments(int pageNumber, int sizePage, String orderBy, String sortDirection) {
        throw new UnsupportedOperationException("Unimplemented method 'getAllComents'");
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto getCommentById(long publicationId, long id) {

             //Verifico que exista le publicación
             Optional<PublicationEntity> publication = publicationRepository.findById(publicationId);
             if(publication.isEmpty()){
                throw ResourceNotFoundException.builder()
                .nombreDelRecurso("publicacion")
                .nombreDelCampo("id")
                .valorDelCampo(publicationId)
                .message("Publicación no encontrada")
                .build();
             }

             //Verifico que exista el comentario
             Optional<CommentEntity> comment = commentRepository.findById(id);
             if(comment.isEmpty()){
                throw ResourceNotFoundException.builder()
                .nombreDelRecurso("comentario")
                .nombreDelCampo("id")
                .valorDelCampo(id)
                .message("El comentario no existe")
                .build();
             }
            
             //Verifico que el comentario sea de esa publicación 
             if(comment.get().getPublication().getId() != publication.get().getId()){
                throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación ");
            }
            
          return CommentMapper.entityToDto(comment.get());
    }

    @Override
    @Transactional
    public CommentDto updateComment(long publicationId, CommentDto commentDto, long id) {
           //Verifico que exista le publicación
           Optional<PublicationEntity> publication = publicationRepository.findById(publicationId);
           if(publication.isEmpty()){
              throw ResourceNotFoundException.builder()
              .nombreDelRecurso("publicacion")
              .nombreDelCampo("id")
              .valorDelCampo(publicationId)
              .message("Publicación no encontrada")
              .build();
           }

           //Verifico que exista el comentario
           Optional<CommentEntity> comment = commentRepository.findById(id);
           if(comment.isEmpty()){
              throw ResourceNotFoundException.builder()
              .nombreDelRecurso("comentario")
              .nombreDelCampo("id")
              .valorDelCampo(id)
              .message("El comentario no existe")
              .build();
           }
          
           //Verifico que el comentario sea de esa publicación 
           if(comment.get().getPublication().getId() != publication.get().getId()){
              throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación ");
          }

          CommentEntity updateComment = comment.get();
          updateComment.setId(id);
          updateComment.setName(commentDto.getName());
          updateComment.setEmail(commentDto.getEmail());
          updateComment.setBody(commentDto.getBody());

          CommentEntity commentEntity = commentRepository.save(updateComment);
          return CommentMapper.entityToDto(commentEntity);
    }

    @Override
    @Transactional
    public void deletCommentById(long publicationId,long id) {
           //Verifico que exista le publicación
           Optional<PublicationEntity> publication = publicationRepository.findById(publicationId);
           if(publication.isEmpty()){
              throw ResourceNotFoundException.builder()
              .nombreDelRecurso("publicacion")
              .nombreDelCampo("id")
              .valorDelCampo(publicationId)
              .message("Publicación no encontrada")
              .build();
           }

           //Verifico que exista el comentario
           Optional<CommentEntity> comment = commentRepository.findById(id);
           if(comment.isEmpty()){
              throw ResourceNotFoundException.builder()
              .nombreDelRecurso("comentario")
              .nombreDelCampo("id")
              .valorDelCampo(id)
              .message("El comentario no existe")
              .build();
           }
          
           //Verifico que el comentario sea de esa publicación 
           if(comment.get().getPublication().getId() != publication.get().getId()){
              throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación ");
          }

          //Elimino el comentario 
          commentRepository.delete(comment.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getAllCommentByPublicationId(long publicationId) {
        //Obtengo lista de comentarios por el id de la publicación
         List<CommentEntity> comments = commentRepository.findByPublicationId(publicationId);
        //La convierto a una lista de dtos para mostrar
         return comments.stream().map(CommentMapper::entityToDto).collect(Collectors.toList());    
    }
}
