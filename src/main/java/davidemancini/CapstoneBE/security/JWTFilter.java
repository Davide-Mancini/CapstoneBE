package davidemancini.CapstoneBE.security;

import davidemancini.CapstoneBE.entities.Utenti;
import davidemancini.CapstoneBE.exceptions.MyUnauthorizedException;
import davidemancini.CapstoneBE.services.UtenteService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UtenteService utenteService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String token = null;
        if (header!=null&&header.startsWith("Bearer ")){
        token= header.replace("Bearer ", "");
        }
        if (token==null&& request.getCookies()!= null){
            for (Cookie cookie: request.getCookies()){
                if ("jtw".equals(cookie.getName())){
                    token=cookie.getValue();
                    break;
                }
            }
        }
        if (token==null){
            filterChain.doFilter(request,response);
            return;
        }

        jwtTools.verificaToken(token);
        UUID utenteId = jwtTools.idFromToken(token);
        Utenti trovato = utenteService.findById(utenteId);
        Authentication authentication = new UsernamePasswordAuthenticationToken(trovato,null,trovato.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**",request.getServletPath());
    }
}
