package com.springboot.blog.springboot_blog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.springboot_blog.exceptions.BlogAppException;
import com.springboot.blog.springboot_blog.exceptions.ResourceNotFoundException;
import com.springboot.blog.springboot_blog.models.dtos.ComentDto;
import com.springboot.blog.springboot_blog.models.entities.ComentEntity;
import com.springboot.blog.springboot_blog.models.entities.PublicationEntity;
import com.springboot.blog.springboot_blog.models.mappers.ComentMapper;
import com.springboot.blog.springboot_blog.repositories.ComentRepository;
import com.springboot.blog.springboot_blog.repositories.PublicationRepository;

@Service
public class ComentServiceImp implements ComentService {

    @Autowired
    private ComentRepository comentRepository;
    @Autowired
    private PublicationRepository publicationRepository;

    @Override
    public ComentDto creatComent(long publicationId, ComentDto comentDto) {
         //Convierto de dto a entidad para crear el comentario 
        ComentEntity comentEntity = ComentMapper.dtoToEntity(comentDto);

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
         comentEntity.setPublication(publication.get());
         ComentEntity comentEntity2 = comentRepository.save(comentEntity);

         //Convierto de entidad a dto para mostrar la respuesta
        return ComentMapper.entityToDto(comentEntity2);
    }

    @Override
    public Page<ComentDto> getAllComents(int pageNumber, int sizePage, String orderBy, String sortDirection) {
        throw new UnsupportedOperationException("Unimplemented method 'getAllComents'");
    }

    @Override
    public ComentDto getComentById(long publicationId, long id) {

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
             Optional<ComentEntity> coment = comentRepository.findById(id);
             if(coment.isEmpty()){
                throw ResourceNotFoundException.builder()
                .nombreDelRecurso("comentario")
                .nombreDelCampo("id")
                .valorDelCampo(id)
                .message("El comentario no existe")
                .build();
             }
            
             //Verifico que el comentario sea de esa publicación 
             if(coment.get().getPublication().getId() != publication.get().getId()){
                throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación ");
            }
            
          return ComentMapper.entityToDto(coment.get());
    }

    @Override
    public ComentDto updateComent(long publicationId, ComentDto comentDto, long id) {
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
           Optional<ComentEntity> coment = comentRepository.findById(id);
           if(coment.isEmpty()){
              throw ResourceNotFoundException.builder()
              .nombreDelRecurso("comentario")
              .nombreDelCampo("id")
              .valorDelCampo(id)
              .message("El comentario no existe")
              .build();
           }
          
           //Verifico que el comentario sea de esa publicación 
           if(coment.get().getPublication().getId() != publication.get().getId()){
              throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación ");
          }

          ComentEntity updateComent = coment.get();
          updateComent.setId(id);
          updateComent.setName(comentDto.getName());
          updateComent.setEmail(comentDto.getEmail());
          updateComent.setBody(comentDto.getBody());

          ComentEntity comentEntity = comentRepository.save(updateComent);
          return ComentMapper.entityToDto(comentEntity);
    }

    @Override
    public void deletComentById(long publicationId,long id) {
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
           Optional<ComentEntity> coment = comentRepository.findById(id);
           if(coment.isEmpty()){
              throw ResourceNotFoundException.builder()
              .nombreDelRecurso("comentario")
              .nombreDelCampo("id")
              .valorDelCampo(id)
              .message("El comentario no existe")
              .build();
           }
          
           //Verifico que el comentario sea de esa publicación 
           if(coment.get().getPublication().getId() != publication.get().getId()){
              throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación ");
          }

          //Elimino el comentario 
          comentRepository.delete(coment.get());
    }

    @Override
    public List<ComentDto> getAllComentByPublicationId(long publicationId) {
        //Obtengo lista de comentarios por el id de la publicación
         List<ComentEntity> coments = comentRepository.findByPublicationId(publicationId);
        //La convierto a una lista de dtos para mostrar
         return coments.stream().map(ComentMapper::entityToDto).collect(Collectors.toList());    
    }

}
