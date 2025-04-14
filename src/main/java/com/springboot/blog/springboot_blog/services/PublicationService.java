package com.springboot.blog.springboot_blog.services;

import org.springframework.data.domain.Page;

import com.springboot.blog.springboot_blog.models.dtos.PublicationDto;

public interface PublicationService {

    public PublicationDto createPublication(PublicationDto publicationDto);

    public Page<PublicationDto> getAllPublications(int pageNumber,int sizePage,String orderBy,String sortDirection);
   
    public PublicationDto getById(long id);

    public PublicationDto updatePublication(PublicationDto publicationDto, long id);

    public void deletPublication(long id);

}