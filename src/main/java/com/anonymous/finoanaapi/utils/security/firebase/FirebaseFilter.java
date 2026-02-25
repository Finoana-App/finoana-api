package com.anonymous.finoanaapi.utils.security.firebase;

import static com.anonymous.finoanaapi.utils.security.SecurityConf.AUTHORIZATION_HEADER;

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
  private final FirebaseTokenVerification tokenVerification;

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

  private void authUser(HttpServletRequest request)
      throws BearerNotFound, AuthorizationHeaderNotFound, FirebaseAuthException {
    var token = getBearer(request);
    var firebaseInfo = tokenVerification.verifyIdToken(token);
    var principal = firebaseUserToPrincipalMapper.apply(firebaseInfo);
    setPrincipal(principal);
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

  private static void filterExceptionToHttpException(
      Exception exception, HttpServletResponse response) {
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    response.setContentType("application/json");
    try {
      response
          .getWriter()
          .write(
              """
              {
                "error": %s
              }
              """
                  .formatted(exception));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      authUser(request);
    } catch (AuthorizationHeaderNotFound e) {
      log.warn(e.getMessage());
      /**
       * TODO: after ignoring request that doesn't required auth, add mapper here request without
       * authorization must raise exception
       */
    } catch (BearerNotFound e) {
      log.warn("Bearer not found in %s".formatted(request.getHeader(AUTHORIZATION_HEADER)));
      filterExceptionToHttpException(e, response);
      return;
    } catch (FirebaseAuthException e) {
      log.warn("User not authenticated, %s".formatted(e));
      filterExceptionToHttpException(e, response);
      return;
    }

    filterChain.doFilter(request, response);
  }
}
