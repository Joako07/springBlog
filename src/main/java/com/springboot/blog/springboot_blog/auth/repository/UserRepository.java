package com.springboot.blog.springboot_blog.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.springboot_blog.auth.user.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

    Optional<UserEntity> findByUsername(String username);

}
