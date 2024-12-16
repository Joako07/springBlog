package com.springboot.blog.springboot_blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.springboot_blog.models.entities.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    public List<CommentEntity> findByPublicationId(long publicationId);

}
