package com.springboot.blog.springboot_blog.models.mappers;

import org.springframework.stereotype.Component;

import com.springboot.blog.springboot_blog.models.dtos.PublicationDto;
import com.springboot.blog.springboot_blog.models.entities.PublicationEntity;

@Component
public class PublicationMapper {

    private PublicationMapper(){

    }

    //Convierto de DTO a Entidad
    public static PublicationEntity dtoToEntity (PublicationDto publicationDto){
        return PublicationEntity.builder()
        .title(publicationDto.getTitle())
        .description(publicationDto.getDescription())
        .content(publicationDto.getContent())
        .build();
    }

    //Convierto de Entidad a DTO
    public static PublicationDto entityToDto(PublicationEntity publicationEntity){
        return PublicationDto.builder()
        .id(publicationEntity.getId())
        .title(publicationEntity.getTitle())
        .description(publicationEntity.getDescription())
        .content(publicationEntity.getContent())
        .build();
    }
}
