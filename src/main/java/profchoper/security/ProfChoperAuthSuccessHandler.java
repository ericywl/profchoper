package profchoper.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

import static profchoper._misc.Constant.ROLE_PROF;
import static profchoper._misc.Constant.ROLE_STUDENT;

@Component
public class ProfChoperAuthSuccessHandler implements AuthenticationSuccessHandler {

    private Log logger = LogFactory.getLog(this.getClass());
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(HttpServletRequest request, HttpServletResponse response,
                          Authentication authentication) throws IOException {

        String targetURL = determineTargetURL(authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. " +
                    "Unable to redirect to " + targetURL);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetURL);
    }

    protected String determineTargetURL(Authentication authentication) {
        boolean isStudent = false;
        boolean isProf = false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals(ROLE_STUDENT)) {
                isStudent = true;
                break;
            } else if (grantedAuthority.getAuthority().equals(ROLE_PROF)) {
                isProf = true;
                break;
            }
        }

        if (isStudent) return "/student.html";
        if (isProf) return "/prof.html";

        throw new IllegalStateException();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return;

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
}
