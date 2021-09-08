package uns.ac.rs.postservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import uns.ac.rs.postservice.domain.User;
import uns.ac.rs.postservice.dto.PostDTO;
import uns.ac.rs.postservice.service.PostService;
import uns.ac.rs.postservice.util.InvalidDataException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER') || hasRole('ROLE_AGENT')")
	@PostMapping(value = "/create")
	public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return new ResponseEntity<>(postService.createPost(postDTO, user.getUsername()), HttpStatus.OK);
		} catch (InvalidDataException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER') || hasRole('ROLE_AGENT')")
	@GetMapping
	public ResponseEntity<?> getAllPosts() {
		try {
			return new ResponseEntity<>(postService.getAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER') || hasRole('ROLE_AGENT')")
	@GetMapping(value = "/userPosts")
	public ResponseEntity<?> getAllPostsByUser() {
		try {
			return new ResponseEntity<>(postService.getAllByUser(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
