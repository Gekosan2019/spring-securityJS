package com.shundalov.spring.security.config.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        if( authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            httpServletResponse.getHeaderNames();
            httpServletRequest.getParameter("username");
            httpServletResponse.sendRedirect("admin");
        }
        else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")) ) {
            httpServletResponse.sendRedirect("user");
            }
        }
    }
