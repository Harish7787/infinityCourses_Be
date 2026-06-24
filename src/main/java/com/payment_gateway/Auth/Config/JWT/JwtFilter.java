//package com.payment_gateway.Auth.Config.JWT;
//
//import com.payment_gateway.Auth.Entity.User;
//import com.payment_gateway.Auth.Repository.UserRepository;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.security.authentication.
//        UsernamePasswordAuthenticationToken;
//
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import org.springframework.security.core.userdetails.UserDetails;
//
//import org.springframework.security.core.userdetails.
//        UserDetailsService;
//
//import org.springframework.security.web.authentication.
//        WebAuthenticationDetailsSource;
//
//import org.springframework.stereotype.Component;
//
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class JwtFilter extends OncePerRequestFilter {
//
//    private final JwtService jwtService;
//
//    // ✅ Use Repository instead of UserDetailsService
//    private final UserRepository userRepository;
//
//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain
//    ) throws ServletException, IOException {
//
//        if (request.getMethod().equals("OPTIONS")) {
//            response.setStatus(HttpServletResponse.SC_OK);
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // Get Authorization Header
//        final String header =
//                request.getHeader("Authorization");
//
//        // Check Bearer Token
//        if (header == null ||
//                !header.startsWith("Bearer ")) {
//
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // Remove "Bearer "
//        String token = header.substring(7);
//
//        // Extract Email
//        String email =
//                jwtService.extractUsername(token);
//
//        // Check Authentication
//        if (email != null &&
//                SecurityContextHolder.getContext()
//                        .getAuthentication() == null) {
//
//            // Find User
//            User user = userRepository
//                    .findByEmail(email)
//                    .orElse(null);
//
//            // Validate Token
//            if (user != null &&
//                    jwtService.isTokenValid(
//                            token,
//                            user.getEmail()
//                    )) {
//                System.out.println(user.getRole());
//                // Create Role
//                SimpleGrantedAuthority authority =
//                        new SimpleGrantedAuthority(
//                                "ROLE_" + user.getRole()
//                        );
//
//                // Create Authentication Token
//                UsernamePasswordAuthenticationToken authToken =
//                        new UsernamePasswordAuthenticationToken(
//                                user.getEmail(),
//                                null,
//                                List.of(authority)
//                        );
//
//                authToken.setDetails(
//                        new WebAuthenticationDetailsSource()
//                                .buildDetails(request)
//                );
//
//                // Set Authentication
//                SecurityContextHolder.getContext()
//                        .setAuthentication(authToken);
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}

package com.payment_gateway.Auth.Config.JWT;

import com.payment_gateway.Auth.Entity.User;
import com.payment_gateway.Auth.Repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication
        .UsernamePasswordAuthenticationToken;

import org.springframework.security.core.authority
        .SimpleGrantedAuthority;

import org.springframework.security.core.context
        .SecurityContextHolder;

import org.springframework.security.web.authentication
        .WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import io.jsonwebtoken.ExpiredJwtException;
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserRepository userRepository;

//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain
//    ) throws ServletException, IOException {
//
//        //  Allow OPTIONS Request
//        if (request.getMethod().equals("OPTIONS")) {
//
//            response.setStatus(HttpServletResponse.SC_OK);
//
//            filterChain.doFilter(request, response);
//
//            return;
//        }
//
//        //  Get Authorization Header
//        final String header =
//                request.getHeader("Authorization");
//
//        // Check Bearer Token
//        if (header == null ||
//                !header.startsWith("Bearer ")) {
//
//            filterChain.doFilter(request, response);
//
//            return;
//        }
//
//        //  Remove Bearer
//        String token = header.substring(7);
//
//        //  Extract Email
//        String email =
//                jwtService.extractUsername(token);
//
//        //  Extract Role From Token
//        String role =
//                jwtService.extractRole(token);
//
//        //  Check Authentication
//        if (email != null &&
//                SecurityContextHolder
//                        .getContext()
//                        .getAuthentication() == null) {
//
//            //  Find User
//            User user = userRepository
//                    .findByEmail(email)
//                    .orElse(null);
//
//            //  Validate Token
//            if (user != null &&
//                    jwtService.isTokenValid(
//                            token,
//                            user.getEmail()
//                    )) {
//
//                System.out.println("ROLE : " + role);
//
//                //  Create Authority
//                SimpleGrantedAuthority authority =
//                        new SimpleGrantedAuthority(role);
//
//                //  Create Authentication Token
//                UsernamePasswordAuthenticationToken authToken =
//                        new UsernamePasswordAuthenticationToken(
//                                email,
//                                null,
//                                List.of(authority)
//                        );
//
//                authToken.setDetails(
//                        new WebAuthenticationDetailsSource()
//                                .buildDetails(request)
//                );
//
//                //  Set Authentication
//                SecurityContextHolder
//                        .getContext()
//                        .setAuthentication(authToken);
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
@Override
protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
) throws ServletException, IOException {

    try {

        final String header =
                request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        String email = jwtService.extractUsername(token);

        String role = jwtService.extractRole(token);

        if (email != null &&
                SecurityContextHolder.getContext()
                        .getAuthentication() == null) {

            User user = userRepository
                    .findByEmail(email)
                    .orElse(null);

            if (user != null &&
                    jwtService.isTokenValid(
                            token,
                            user.getEmail()
                    )) {

                SimpleGrantedAuthority authority =
                        new SimpleGrantedAuthority(role);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(authority)
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);

    } catch (ExpiredJwtException ex) {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        response.setContentType("application/json");

        response.getWriter().write("""
            {
              "message":"Token Expired"
            }
            """);

    }
}
}