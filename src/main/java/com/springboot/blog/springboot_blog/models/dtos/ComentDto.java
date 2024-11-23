package com.springboot.blog.springboot_blog.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComentDto {

    private Long id;
    private String name;
    private String email;
    private String body;
}
