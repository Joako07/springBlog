package com.springboot.blog.springboot_blog.models.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springboot.blog.springboot_blog.models.dtos.CommentDto;
import com.springboot.blog.springboot_blog.models.entities.CommentEntity;

@Component
public class CommentMapper {

    @Autowired
    private static ModelMapper modelMapper;

    private CommentMapper(ModelMapper modelMapper) {
        CommentMapper.modelMapper = modelMapper;
    }

    // Convierto de DTO a Entidad
    public static CommentEntity dtoToEntity(CommentDto commentDto) {
        CommentEntity commentEntity = modelMapper.map(commentDto, CommentEntity.class);
        return commentEntity;
    }

    // Convierto de Entidad a DTO
    public static CommentDto entityToDto(CommentEntity commentEntity) {
        CommentDto commentDto = modelMapper.map(commentEntity, CommentDto.class);
        return commentDto;
    }
}
