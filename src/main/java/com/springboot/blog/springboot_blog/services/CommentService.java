package com.springboot.blog.springboot_blog.services;

import java.util.List;

import com.springboot.blog.springboot_blog.models.dtos.CommentDto;

public interface CommentService {

    public CommentDto createComment(long publicationId, CommentDto commentDto);

    public CommentDto getCommentById(long publicationId, long id);

    public CommentDto updateComment(long publicationId, CommentDto commentDto, long id);

    public void deleteCommentById(long publicationId, long id);

    public List<CommentDto> getAllCommentByPublicationId(long publicationId);
}
