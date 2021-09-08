package uns.ac.rs.postservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import uns.ac.rs.postservice.service.UserService;
import uns.ac.rs.postservice.util.InvalidDataException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER') || hasRole('ROLE_AGENT')")
	@GetMapping(value = "/forTagging")
	public ResponseEntity<?> getAllUsersForTagging() {
		try {
			return new ResponseEntity<>(userService.getAllUsersForTagging(), HttpStatus.OK);
		} catch (InvalidDataException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
