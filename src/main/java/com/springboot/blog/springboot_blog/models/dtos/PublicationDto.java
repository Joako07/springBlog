package com.springboot.blog.springboot_blog.models.dtos;

import java.util.Set;

import com.springboot.blog.springboot_blog.models.entities.ComentEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicationDto {

    private Long id;
    private String title;
    private String description;
    private String content;
    private Set<ComentEntity> coments;

}