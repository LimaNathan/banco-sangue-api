package wk.banco.sangue.api.configs.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import wk.banco.sangue.api.configs.exception.runtime.WkSangueException;
import wk.banco.sangue.api.services.CustomUserDetailsServiceImpl;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtRequestFilter.class);

    private final JwtTokenUtil jwtTokenUtil;

    private final CustomUserDetailsServiceImpl userDetailsService;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException, WkSangueException {

        AntPathMatcher pathMatcher = new AntPathMatcher();
        if (pathMatcher.match("/auth/login", request.getRequestURI())
                || pathMatcher.match("/auth/register", request.getRequestURI())
                || pathMatcher.match("/v3/api-docs/**", request.getRequestURI())
                || pathMatcher.match("/fav.ico", request.getRequestURI())
                || pathMatcher.match("/swagger-ui/**", request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        final String requestTokenHeader = request.getHeader("authorization");

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
            log.error("Token não encontrado.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token nao encontrado ou formato invalido.");
            chain.doFilter(request, response);
            return;
        }

        jwtToken = requestTokenHeader.substring(7);

        try {
            username = jwtTokenUtil.extractUsername(jwtToken);
        } catch (IllegalArgumentException e) {
            log.error("Nao foi possível verificar o token");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token invalido.");
            chain.doFilter(request, response);
            return;

        } catch (ExpiredJwtException e) {
            log.error("Token foi expirado");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expirado.");

            chain.doFilter(request, response);
            return;

        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            final boolean isTokenValid = jwtTokenUtil.validateToken(jwtToken, userDetails);
            if (isTokenValid) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }

}
