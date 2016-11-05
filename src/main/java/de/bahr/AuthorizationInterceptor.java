package de.bahr;

import de.bahr.user.User;
import de.bahr.user.UserRepository;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by michaelbahr on 27/10/16.
 */

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Autowired
    UserRepository userRepository;

    private String decode(String s) {
        return StringUtils.newStringUtf8(Base64.decodeBase64(s));
    }

    private boolean isInvalid(String authorization, String requiredRole) {
        assert null != requiredRole;

        String decoded = decode(authorization.replace("Basic ", ""));
        String characterId = decoded.split(":")[0];
        User user = userRepository.findByCharacterId(Long.valueOf(characterId));

        if (user != null && user.getRole() != null && user.getRole().contains(requiredRole)) {
            return false;
        } else {
            return true;
        }
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        String requiredRole = getRequiredRole(request.getRequestURI());
        String token = request.getHeader("authorization");

        if (requiredRole.equals("NONE")) {
            return true;
        }

        if (isInvalid(token, requiredRole)) {
            response.sendError(403);
            return false;
        } else {
            return true;
        }
    }

    private String getRequiredRole(String uri) {
        // looks like /secured/manager/...
        String[] split = uri.split("/");

        // uri may be things like /error, in that case it is allowed
        boolean isSecured = false;
        for (String uriPart : split) {
            if (uriPart.toLowerCase().contains("secured")) {
                isSecured = true;
            }
        }

        if (!isSecured) {
            return "NONE";
        } else {
            String scope = split[2];
            return scope.toUpperCase();
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {

    }
}