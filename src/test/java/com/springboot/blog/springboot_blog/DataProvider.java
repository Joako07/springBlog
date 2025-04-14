package com.springboot.blog.springboot_blog;

import static org.mockito.Mockito.mockStatic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.springboot.blog.springboot_blog.models.dtos.CommentDto;
import com.springboot.blog.springboot_blog.models.dtos.PublicationDto;
import com.springboot.blog.springboot_blog.models.entities.CommentEntity;
import com.springboot.blog.springboot_blog.models.entities.PublicationEntity;
import com.springboot.blog.springboot_blog.models.mappers.CommentMapper;
import com.springboot.blog.springboot_blog.models.mappers.PublicationMapper;

public class DataProvider {

    public static List<PublicationEntity> listSamplePublications(){

        PublicationEntity publicationEntity1  = new PublicationEntity();
        publicationEntity1.setId(1L);
        publicationEntity1.setTitle("Mi primera Publicacion de prueba");
        publicationEntity1.setDescription("Breve descripción");
        publicationEntity1.setContent("Contenido de la publicación");
        publicationEntity1.setComments(listSampleComments(publicationEntity1));

        PublicationEntity publicationEntity2  = new PublicationEntity();
        publicationEntity2.setId(2L);
        publicationEntity2.setTitle("Mi segunda Publicacion de prueba");
        publicationEntity2.setDescription("Breve descripción");
        publicationEntity2.setContent("Contenido de la publicación");
        publicationEntity2.setComments(listSampleComments(publicationEntity2));
   

        return  List.of(publicationEntity1,publicationEntity2);
    }

    public static Set<CommentEntity> listSampleComments(PublicationEntity publicationEntity){
        
        CommentEntity commentEntity1 = new CommentEntity();
        commentEntity1.setId(1L);
        commentEntity1.setName("Juan Manuel");
        commentEntity1.setEmail("Jaunm@gmail.com");
        commentEntity1.setBody("Hola soy Juan este es mi comentario");
        commentEntity1.setPublication(publicationEntity);
        
        CommentEntity commentEntity2 = new CommentEntity();
        commentEntity2.setId(1L);
        commentEntity2.setName("Miguel Abuelo");
        commentEntity2.setEmail("AbuelodelaNada@gmail.com");
        commentEntity2.setBody("Hola soy Miguel este es mi comentario");
        commentEntity2.setPublication(publicationEntity);
    
        return new HashSet<>(Set.of(commentEntity1, commentEntity2));
    }

    public static PublicationEntity newPublicationEntity(){

        PublicationEntity publicationEntity  = new PublicationEntity();
        publicationEntity.setId(1L);
        publicationEntity.setTitle("Mi primera Publicacion de prueba");
        publicationEntity.setDescription("Breve descripción");
        publicationEntity.setContent("Contenido de la publicación");
        publicationEntity.setComments(listSampleComments(publicationEntity));

        return publicationEntity;
    }

    public static PublicationDto newPublicationDto(){

        PublicationDto publicationDto  = new PublicationDto();
        publicationDto.setId(1L);
        publicationDto.setTitle("Mi primera Publicacion de prueba");
        publicationDto.setDescription("Breve descripción");
        publicationDto.setContent("Contenido de la publicación");
        publicationDto.setComments(listSampleComments(publicationDto));

        return publicationDto;
    }

}
