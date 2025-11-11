package Immobiliaris.Progetto_Rooftop.Security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwt;

    public JwtAuthFilter(JwtService jwt) {
        this.jwt = jwt;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String header = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                DecodedJWT decoded = jwt.verify(token);
                String userId = decoded.getSubject();
                String ruolo = decoded.getClaim("ruolo").asString();

                var auth = new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        ruolo != null ? List.of(new SimpleGrantedAuthority("ROLE_" + ruolo)) : List.of()
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception ignored) {
                // token invalido/scaduto → niente auth nel contesto
                SecurityContextHolder.getContext().setAuthentication(null);
            }
        }
        chain.doFilter(req, res);
    }
}