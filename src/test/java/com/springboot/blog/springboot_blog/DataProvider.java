package com.springboot.blog.springboot_blog;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.springboot.blog.springboot_blog.models.dtos.CommentDto;
import com.springboot.blog.springboot_blog.models.entities.CommentEntity;
import com.springboot.blog.springboot_blog.models.entities.PublicationEntity;

public class DataProvider {

    public static List<PublicationEntity> listSamplePublications() {

        PublicationEntity publicationEntity1 = new PublicationEntity();
        publicationEntity1.setId(1L);
        publicationEntity1.setTitle("Mi primera Publicacion de prueba");
        publicationEntity1.setDescription("Breve descripción");
        publicationEntity1.setContent("Contenido de la publicación");
        publicationEntity1.setComments(hasSampleComments(publicationEntity1));

        PublicationEntity publicationEntity2 = new PublicationEntity();
        publicationEntity2.setId(2L);
        publicationEntity2.setTitle("Mi segunda Publicacion de prueba");
        publicationEntity2.setDescription("Breve descripción");
        publicationEntity2.setContent("Contenido de la publicación");
        publicationEntity2.setComments(hasSampleComments(publicationEntity2));

        return List.of(publicationEntity1, publicationEntity2);
    }

    public static Set<CommentEntity> hasSampleComments(PublicationEntity publicationEntity) {

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

    public static List<CommentEntity> listSampleComments2(PublicationEntity publicationEntity) {

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

        return List.of(commentEntity1, commentEntity2);
    }

    public static List<CommentDto> listSampleCommentsDtos() {

        CommentDto commentDto1 = new CommentDto();
        commentDto1.setId(1L);
        commentDto1.setName("Juan Manuel");
        commentDto1.setEmail("Jaunm@gmail.com");
        commentDto1.setBody("Hola soy Juan este es mi comentario");
   

        CommentDto commentDto2 = new CommentDto();
        commentDto2.setId(1L);
        commentDto2.setName("Miguel Abuelo");
        commentDto2.setEmail("AbuelodelaNada@gmail.com");
        commentDto2.setBody("Hola soy Miguel este es mi comentario");
   
        return List.of(commentDto1, commentDto2);     
    }

    public static PublicationEntity newPublicationEntity() {

        PublicationEntity publicationEntity = new PublicationEntity();
        publicationEntity.setId(1L);
        publicationEntity.setTitle("Mi primera Publicacion de prueba");
        publicationEntity.setDescription("Breve descripción");
        publicationEntity.setContent("Contenido de la publicación");
        publicationEntity.setComments(hasSampleComments(publicationEntity));

        return publicationEntity;
    }

    public static PublicationEntity updatedPublicationEntity() {

        PublicationEntity publicationEntity = new PublicationEntity();
        publicationEntity.setId(1L);
        publicationEntity.setTitle("Mi publicación Actualizada");
        publicationEntity.setDescription("Gran descripción");
        publicationEntity.setContent("Contenido");
        publicationEntity.setComments(hasSampleComments(publicationEntity));

        return publicationEntity;
    }

    public static CommentEntity newCommentEntity(PublicationEntity publicationEntity) {

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(1L);
        commentEntity.setName("Juan Manuel");
        commentEntity.setEmail("Jaunm@gmail.com");
        commentEntity.setBody("Hola soy Juan este es mi comentario");
        commentEntity.setPublication(publicationEntity);

        return commentEntity;
    }

    public static CommentDto newCommentDto() {

        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setName("Juan Manuel");
        commentDto.setEmail("Jaunm@gmail.com");
        commentDto.setBody("Hola soy Juan este es mi comentario");

        return commentDto;
    }

    public static CommentEntity newUpdateCommentEntity(PublicationEntity publicationEntity) {

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(1L);
        commentEntity.setName("Juaquin Manuel");
        commentEntity.setEmail("Juako@gmail.com");
        commentEntity.setBody("Hola soy Juaquin este es mi comentario");
        commentEntity.setPublication(publicationEntity);

        return commentEntity;
    }

    public static CommentDto newUpdateCommentDto() {

        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setName("Juaquin Manuel");
        commentDto.setEmail("Juako@gmail.com");
        commentDto.setBody("Hola soy Juaquin este es mi comentario");

        return commentDto;
    }

}
