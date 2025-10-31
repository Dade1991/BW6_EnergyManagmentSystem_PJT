package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.security;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Utente;
import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.services.UtentiService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.UnavailableException;
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

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UtentiService utentiService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //---------------------------------- [AUTENTICAZIONE] ----------------------------------

        // 1. Verifica della presenza dell'header
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnavailableException("Inserire il token nell'authorization header e nel formato giusto");
        }

        // 2. Verifica della presenza del token
        String accessToken = authHeader.replace("Bearer ", "");
        jwtTools.verifyToken(accessToken);


        //---------------------------------- [AUTORIZZAZIONE] ----------------------------------
        // 1. Ricerca dell'utente nel DB
        Long idUtente = jwtTools.exctractIdFromToken(accessToken);
        Utente utenteFound = this.utentiService.findUtentiById(idUtente);
        // 2. Associazione dell'utente al Security Context
        Authentication authentication = new UsernamePasswordAuthenticationToken(utenteFound, null, utenteFound.getAuthorities());
        // 3. Aggiornamento del Security Context associando ad esso l'utente corrente e il suo ruolo
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    // Disattivazione dei filtri per determinati endpoint

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
