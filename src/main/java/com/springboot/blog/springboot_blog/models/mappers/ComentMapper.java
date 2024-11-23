package com.springboot.blog.springboot_blog.models.mappers;

import org.springframework.stereotype.Component;

import com.springboot.blog.springboot_blog.models.dtos.ComentDto;
import com.springboot.blog.springboot_blog.models.entities.ComentEntity;

@Component
public class ComentMapper {

    private ComentMapper(){
    
    }

    //Convierto de DTO a Entidad
    public static ComentEntity dtoToEntity (ComentDto comentDto){
        return ComentEntity.builder()
                .name(comentDto.getName())
                .email(comentDto.getEmail())
                .body(comentDto.getBody())
                .build();
    }

    //Convierto de Entidad a DTO
    public static ComentDto entityToDto(ComentEntity comentEntity){
        return ComentDto.builder()
                .id(comentEntity.getId())
                .name(comentEntity.getName())
                .email(comentEntity.getEmail())
                .body(comentEntity.getBody())
                .build();
    }

}
