package de.bahr.client;

import de.bahr.user.User;
import de.bahr.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static de.bahr.user.UserUtil.getUser;
import static org.springframework.http.HttpStatus.OK;

/**
 * Created by michaelbahr on 13/04/16.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/secured/client/reorder")
public class ClientReorderController {

    @Autowired
    ReorderRepository reorderRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/doctrine", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> doctrine() {
        List<Reorder> doctrines = reorderRepository.findAllByOwner("doctrine");
        return new ResponseEntity<>(doctrines, OK);
    }

    @RequestMapping(value = "/own", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> history(@RequestHeader("authorization") String auth) {
        User user = getUser(auth, userRepository);
        List<Reorder> reorders = reorderRepository.findAllByOwner(user.getName());
        return new ResponseEntity<>(reorders, OK);
    }

}
