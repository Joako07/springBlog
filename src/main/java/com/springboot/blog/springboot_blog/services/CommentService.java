package com.springboot.blog.springboot_blog.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.springboot.blog.springboot_blog.models.dtos.CommentDto;

public interface CommentService {

    public CommentDto creatComment(long publicationId, CommentDto commentDto);

    public Page<CommentDto> getAllComments(int pageNumber, int sizePage,String orderBy,String sortDirection);

    public CommentDto getCommentById(long publicationId, long id);

    public CommentDto updateComment(long publicationId, CommentDto commentDto, long id);

    public void deletCommentById(long publicationId, long id);

    public List<CommentDto> getAllCommentByPublicationId(long publicationId);
}
