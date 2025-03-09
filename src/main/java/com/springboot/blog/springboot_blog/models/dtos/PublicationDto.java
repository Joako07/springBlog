package com.springboot.blog.springboot_blog.models.dtos;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicationDto {

    private Long id;

    @NotEmpty
    @Size(min = 2, message = "El titulo deberia tener al menos 2 caracteres")
    private String title;

    @NotEmpty
    @Size(min = 5, message = "La descripción deberia tener al menos 5 caracteres")
    private String description;

    @NotBlank(message = "El campo content no debe estar en blanco")
    private String content;

    private Set<CommentDto> comments = new HashSet<>();
}