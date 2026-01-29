package com.anonymous.finoanaapi.utils.security.firebase;

import static com.anonymous.finoanaapi.utils.security.SecurityConf.AUTHORIZATION_HEADER;
import static com.anonymous.finoanaapi.utils.security.firebase.FirebaseInitialisation.verifyIdToken;

import com.anonymous.finoanaapi.models.Principal;
import com.anonymous.finoanaapi.utils.exceptions.AuthorizationHeaderNotFound;
import com.anonymous.finoanaapi.utils.exceptions.BearerNotFound;
import com.google.firebase.auth.FirebaseAuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@AllArgsConstructor
public class FirebaseFilter extends OncePerRequestFilter {
  public static final String BEARER_PREFIX = "Bearer ";
  private final FirebaseUserToPrincipalMapper firebaseUserToPrincipalMapper;

  private static String getAuthHeader(HttpServletRequest request)
      throws AuthorizationHeaderNotFound {
    var headerValue = request.getHeader(AUTHORIZATION_HEADER);

    if (headerValue == null) {
      throw new AuthorizationHeaderNotFound();
    }

    return headerValue;
  }

  private static String getBearer(HttpServletRequest request)
      throws BearerNotFound, AuthorizationHeaderNotFound {
    var headerValue = getAuthHeader(request);

    if (!headerValue.startsWith(BEARER_PREFIX)) {
      throw new BearerNotFound();
    }

    return headerValue.substring(BEARER_PREFIX.length());
  }

  private void authUser(HttpServletRequest request) {
    try {
      var token = getBearer(request);
      var firebaseInfo = verifyIdToken(token);
      var principal = firebaseUserToPrincipalMapper.apply(firebaseInfo);
      setPrincipal(principal);
      // TODO: better exception handing for http compatibility
    } catch (FirebaseAuthException e) {
      log.warn("User not authenticated, %s".formatted(e));
    } catch (BearerNotFound e) {
      log.warn("Bearer not found in %s".formatted(request.getHeader(AUTHORIZATION_HEADER)));
    } catch (AuthorizationHeaderNotFound e) {
      log.warn(e.getMessage());
    }
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    authUser(request);

    filterChain.doFilter(request, response);
  }

  private static void setPrincipal(Principal principal) {
    SecurityContextHolder.getContext()
        .setAuthentication(
            new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities()));
  }

  public static Principal getPrincipal() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      throw new RuntimeException("No authentification register");
    }

    var rawPrincipal = authentication.getPrincipal();
    if (rawPrincipal == null) {
      throw new RuntimeException("No principal register");
    }

    return (Principal) rawPrincipal;
  }
}
