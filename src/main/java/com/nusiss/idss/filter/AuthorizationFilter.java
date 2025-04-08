package com.nusiss.idss.filter;

import com.nusiss.idss.service.UserService;
import com.nusiss.idss.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(1)
public class AuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String userName = extractUserNameFromToken(request); // implement this
        String path = request.getRequestURI();
        String method = request.getMethod();
        String permissionKey = method + ":" + path;

        if (StringUtils.isNotEmpty(userName)) {
            if(!userService.userHasPermission(userName, permissionKey)){
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write("You don't have permission.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    public String extractUserNameFromToken(HttpServletRequest request) {
        String token = null;

        // Try from Authorization header
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        try {
            return jwtUtils.extractUserName(token);
        } catch (Exception e){
            throw new RuntimeException("JWT Token not found in request." + e.getMessage());
        }

    }

}
