package de.bahr.user;

import de.bahr.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.handler.MappedInterceptor;

/**
 * Created by michaelbahr on 23/10/16.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/secured/manager/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Bean
    @Autowired
    public MappedInterceptor getMappedInterceptor(AuthorizationInterceptor authorizationInterceptor) {
        return new MappedInterceptor(new String[] { "/**" }, authorizationInterceptor);
    }

}
