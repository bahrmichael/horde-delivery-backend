package de.bahr.user;

import de.bahr.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.handler.MappedInterceptor;

import static de.bahr.user.UserUtil.getUser;

/**
 * Created by michaelbahr on 23/10/16.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/details", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> name(@RequestHeader("authorization") String auth) {
        return new ResponseEntity<>(getUser(auth, userRepository), HttpStatus.OK);
    }

    @Bean
    @Autowired
    public MappedInterceptor getMappedInterceptor(AuthorizationInterceptor authorizationInterceptor) {
        return new MappedInterceptor(new String[] { "/**" }, authorizationInterceptor);
    }

}
