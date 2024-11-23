package com.springboot.blog.springboot_blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.springboot_blog.models.entities.ComentEntity;

public interface ComentRepository extends JpaRepository<ComentEntity, Long>{

    public List<ComentEntity> findByPublicationId(long publicationId);

}
