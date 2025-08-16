# springBlog

Este proyecto consiste en una API RESTful desarrollada para la gestión de un blog, permitiendo realizar operaciones CRUD (crear, leer, actualizar y eliminar) sobre publicaciones y comentarios. Está construida utilizando Spring Boot.

La autenticación y autorización del sistema fue implementada mediante Spring Security junto con JWT (JSON Web Token), permitiendo proteger los endpoints y asegurar que solo los usuarios autenticados puedan acceder a ciertos recursos.

Se desarrollaron pruebas unitarias con JUnit 5 y Mockito, asegurando la confiabilidad y el correcto funcionamiento de los servicios principales del sistema.

Visualización con jaCoCo de las puebas unitarias 
<img width="900" height="800" src="https://github.com/Joako07/Imagenes/blob/main/SpringBlog/jacoco.png"/>

Además, la documentación completa de los endpoints fue generada con Swagger, lo que facilita la exploración, prueba y entendimiento de la API.

<img width="900" height="800" src="https://github.com/Joako07/Imagenes/blob/main/SpringBlog/swagger.png"/>

Primero, debes crear un usuario en el endpoint auth/register y luego iniciar sesión en auth/login con el usuario creado y el token generado.

<h2>Base de datos: springboot_blog </h2>
