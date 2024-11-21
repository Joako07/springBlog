package com.springboot.blog.springboot_blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.springboot_blog.models.entities.PublicationEntity;

public interface PublicationRepository extends JpaRepository<PublicationEntity, Long> {

}
