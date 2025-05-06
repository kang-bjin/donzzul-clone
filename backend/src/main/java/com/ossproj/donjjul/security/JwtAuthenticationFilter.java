package com.ossproj.donjjul.security;

import com.ossproj.donjjul.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        try {
            System.out.println(">>> JwtAuthenticationFilter 진입");

            Claims claims = jwtUtil.parseClaims(token);
            String userId = claims.getSubject();
            System.out.println(">> JWT subject (userId): " + userId);

            CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(userId);
            System.out.println(">> 인증할 사용자: " + userDetails.getUsername());
            System.out.println(">> 권한: " + userDetails.getAuthorities());

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println(">> 인증 성공: SecurityContext에 등록 완료");

        } catch (Exception e) {
            System.out.println(">> JWT 인증 실패:");
            e.printStackTrace();
        }

        chain.doFilter(request, response);
    }
}
