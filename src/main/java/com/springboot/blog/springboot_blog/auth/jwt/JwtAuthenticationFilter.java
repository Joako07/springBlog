package com.springboot.blog.springboot_blog.auth.jwt;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@SuppressWarnings("null")
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    //Este metodo realiza los filtros relacionados al Token
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

            //Obtengo el Token    
            final String token = getTokenFromRequest(request);
            final String username;
            
                        //Si el token es null vuelve a la cadena de filtros
                        if (token == null){
                            filterChain.doFilter(request, response);
                            return;
                        }
                        
                        //Caso contrario obtengo el name del token
                        username = jwtService.getUsernameFromToken(token);

                        //Si el name existe y no se encuentra en el ContexHolder lo vamos a buscar a la base de datos
                       if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                            //Si encuentro el name en la base de datos y el token es valido, actualizo el ContexHolder 
                            if(jwtService.isTokenValid(token, userDetails)){
                                UsernamePasswordAuthenticationToken authToken =new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());

                                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                                    SecurityContextHolder.getContext().setAuthentication(authToken);
                            }
                        } 
                      
                        filterChain.doFilter(request, response);
                }
            
                //Obtengo el token sacandole el prefijo Bearer
                private String getTokenFromRequest(HttpServletRequest request) {
                    //Obtengo el encabezado del request el cual contiene el token
                    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

                    //Verifico que exista el texto y que comienze con Bearer. Luego le saco el Bearer
                    if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")){
                        return authHeader.substring(7);
                    }
                    return null;
                }
}
