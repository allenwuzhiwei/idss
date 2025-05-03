package com.nusiss.idss.filter;

import com.nusiss.idss.service.UserService;
import com.nusiss.idss.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationFilter.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.startsWith("/api/users/login")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3")
                || path.startsWith("/actuator/health")) {
            filterChain.doFilter(request, response);
            return;
        }
        String userName = extractUserNameFromToken(request);

        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();

         // implement this

        String method = request.getMethod();
        String permissionKey = method + ":" + path;
        //String userName = auth.getName();
        if (StringUtils.isNotEmpty(userName)) {
            if(!userService.userHasPermission(userName, permissionKey)){
                //return ResponseEntity.status(401).body(new ApiResponse<>(false, "Invalid username or password", "You don't have permission."));

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
