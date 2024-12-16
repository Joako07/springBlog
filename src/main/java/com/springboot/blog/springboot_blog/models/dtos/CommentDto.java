package com.springboot.blog.springboot_blog.models.dtos;

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
public class CommentDto {

    private Long id;

    @NotBlank(message = "El nombre no debe estar vacio")
    @Size(min=2, message ="El titulo deberia tener al menos 2 caracteres")
    private String name;

    @NotBlank(message = "El email no debe estar vacio")
    private String email;

    @NotEmpty
    @Size(min=5, message ="El titulo cuerpo debe tener al menos 5 caracteres")
    private String body;
}
