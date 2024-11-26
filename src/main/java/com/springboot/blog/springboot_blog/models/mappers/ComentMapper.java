package com.springboot.blog.springboot_blog.models.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springboot.blog.springboot_blog.models.dtos.ComentDto;
import com.springboot.blog.springboot_blog.models.entities.ComentEntity;

@Component
public class ComentMapper {
    
    @Autowired
    private static ModelMapper modelMapper;

    private ComentMapper(ModelMapper modelMapper){
        ComentMapper.modelMapper = modelMapper;
    }

       //Convierto de DTO a Entidad
       public static ComentEntity dtoToEntity (ComentDto comentDto){
        ComentEntity comentEntity = modelMapper.map(comentDto, ComentEntity.class);
        return comentEntity;
    }

    //Convierto de Entidad a DTO
    public static ComentDto entityToDto(ComentEntity comentEntity){
        ComentDto comentDto = modelMapper.map(comentEntity, ComentDto.class);
        return comentDto;
    }
}
