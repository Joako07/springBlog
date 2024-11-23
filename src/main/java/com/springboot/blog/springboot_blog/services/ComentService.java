package com.springboot.blog.springboot_blog.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.springboot.blog.springboot_blog.models.dtos.ComentDto;

public interface ComentService {

    public ComentDto creatComent(long publicationId, ComentDto comentDto);

    public Page<ComentDto> getAllComents(int pageNumber, int sizePage,String orderBy,String sortDirection);

    public ComentDto getComentById(long publicationId, long id);

    public ComentDto updateComent(long publicationId, ComentDto comentDto, long id);

    public void deletComentById(long publicationId, long id);

    public List<ComentDto> getAllComentByPublicationId(long publicationId);
}
