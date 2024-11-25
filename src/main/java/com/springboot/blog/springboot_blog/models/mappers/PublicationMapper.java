package com.springboot.blog.springboot_blog.models.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.springboot.blog.springboot_blog.models.dtos.PublicationDto;
import com.springboot.blog.springboot_blog.models.entities.PublicationEntity;

@Component
public class PublicationMapper {

    @Autowired
    private static ModelMapper modelMapper;
            
         private PublicationMapper(ModelMapper modelMapper){
            PublicationMapper.modelMapper = modelMapper;
     }

     //Convierto de DTO a Entidad
     public static PublicationEntity dtoToEntity (PublicationDto publicationDto){
      PublicationEntity publicationEntity = modelMapper.map(publicationDto, PublicationEntity.class);
      return publicationEntity;
    }

    //Convierto de Entidad a DTO
    public static PublicationDto entityToDto(PublicationEntity publicationEntity){
        PublicationDto publicationDto = modelMapper.map(publicationEntity, PublicationDto.class);
        return  publicationDto;
    }
}
