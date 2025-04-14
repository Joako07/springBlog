package com.springboot.blog.springboot_blog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.blog.springboot_blog.exceptions.ResourceNotFoundException;
import com.springboot.blog.springboot_blog.models.dtos.PublicationDto;
import com.springboot.blog.springboot_blog.models.entities.PublicationEntity;
import com.springboot.blog.springboot_blog.models.mappers.PublicationMapper;
import com.springboot.blog.springboot_blog.repositories.PublicationRepository;

@Service
public class PublicationServiceImp implements PublicationService {

   @Autowired
   private PublicationRepository publicationRepository;

   @Override
   @Transactional
   public PublicationDto createPublication(PublicationDto publicationDto) {
      // Convierto de dto a entidad para crear una publicación
      PublicationEntity publicationEntity = PublicationMapper.dtoToEntity(publicationDto);
      publicationRepository.save(publicationEntity);

      // Convierto de entidad a dto para mostrar la respuesta
      return PublicationMapper.entityToDto(publicationEntity);
   }

   @Override
   @Transactional(readOnly = true)
   public Page<PublicationDto> getAllPublications(int pageNumber, int sizePage, String orderBy, String sortDirection) {
      // Creo una Pageable con el cual busco todas las publicaciones y con el map paso uno por una por el mapper
      Sort.Direction direction = "desc".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
      Pageable pageable = PageRequest.of(pageNumber, sizePage, Sort.by(direction, orderBy));
      return publicationRepository.findAll(pageable).map(PublicationMapper::entityToDto);
   }

   @Override
   @Transactional(readOnly = true)
   public PublicationDto getById(long id) {
      // Verifico que exista le publicación
      Optional<PublicationEntity> publication = publicationRepository.findById(id);
      if (publication.isEmpty()) {
         throw ResourceNotFoundException.builder()
               .nombreDelRecurso("publicacion")
               .nombreDelCampo("id")
               .valorDelCampo(id)
               .message("Publicación no encontrada")
               .build();
      }

      return PublicationMapper.entityToDto(publication.get());
   }

   @Override
   @Transactional
   public PublicationDto updatePublication(PublicationDto publicationDto, long id) {
      // Verifico que exista le publicación a actualizar
      Optional<PublicationEntity> publication = publicationRepository.findById(id);
      if (publication.isEmpty()) {
         throw ResourceNotFoundException.builder()
               .nombreDelRecurso("publicacion")
               .nombreDelCampo("id")
               .valorDelCampo(id)
               .message("Publicación no encontrada")
               .build();
      }
      // Actualizo la publicación
      PublicationEntity updatePublication = publication.get();
      updatePublication.setId(id);
      updatePublication.setTitle(publicationDto.getTitle());
      updatePublication.setDescription(publicationDto.getDescription());
      updatePublication.setContent(publicationDto.getContent());

      // Guardo la publicación actualizada
      PublicationEntity publicationDb = publicationRepository.save(updatePublication);
      return PublicationMapper.entityToDto(publicationDb);
   }

   @Override
   @Transactional
   public void deletPublication(long id) {
      // Verifico que exista le publicación a eliminar
      Optional<PublicationEntity> publication = publicationRepository.findById(id);
      if (publication.isEmpty()) {
         throw ResourceNotFoundException.builder()
               .nombreDelRecurso("publicacion")
               .nombreDelCampo("id")
               .valorDelCampo(id)
               .message("Publicación no encontrada")
               .build();
      }
      // Elimino la publicación con el id
      publicationRepository.deleteById(id);
   }
}
