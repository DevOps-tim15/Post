package uns.ac.rs.postservice.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import uns.ac.rs.postservice.domain.User;
import uns.ac.rs.postservice.dto.CommentDTO;
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
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return new ResponseEntity<>(postService.getAllByUser(user.getUsername()), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/publicUsers")
	public ResponseEntity<?> getAllPostsFromPublicUsers() {
		try {
			return new ResponseEntity<>(postService.getAllByPublicUsers(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER') || hasRole('ROLE_AGENT')")
	@GetMapping(value = "/postsToView")
	public ResponseEntity<?> getAllPostsToView() {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			try {
				return new ResponseEntity<>(postService.getAllPostsToView(user.getUsername()), HttpStatus.OK);
			} catch (JsonProcessingException | InterruptedException | ExecutionException e) {
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		} catch (InvalidDataException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER') || hasRole('ROLE_AGENT')")
	@GetMapping(value = "/like/{postId}")
	public ResponseEntity<?> likePost(@PathVariable Long postId) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return new ResponseEntity<>(postService.likePost(postId, user.getUsername()), HttpStatus.OK);
		} catch (InvalidDataException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER') || hasRole('ROLE_AGENT')")
	@GetMapping(value = "/undoLike/{postId}")
	public ResponseEntity<?> undoLikePost(@PathVariable Long postId) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return new ResponseEntity<>(postService.undoLikePost(postId, user.getUsername()), HttpStatus.OK);
		} catch (InvalidDataException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER') || hasRole('ROLE_AGENT')")
	@GetMapping(value = "/undoDislike/{postId}")
	public ResponseEntity<?> undoDislikePost(@PathVariable Long postId) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return new ResponseEntity<>(postService.undoDislikePost(postId, user.getUsername()), HttpStatus.OK);
		} catch (InvalidDataException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER') || hasRole('ROLE_AGENT')")
	@GetMapping(value = "/dislike/{postId}")
	public ResponseEntity<?> dislikePost(@PathVariable Long postId) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return new ResponseEntity<>(postService.dislikePost(postId, user.getUsername()), HttpStatus.OK);
		} catch (InvalidDataException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER') || hasRole('ROLE_AGENT')")
	@GetMapping(value = "/likedAndDisliked")
	public ResponseEntity<?> likedAndDislikedPosts() {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return new ResponseEntity<>(postService.allLikedAndDislikedPosts(user.getUsername()), HttpStatus.OK);
		} catch (InvalidDataException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER') || hasRole('ROLE_AGENT')")
	@GetMapping(value = "/save/{postId}")
	public ResponseEntity<?> savePost(@PathVariable Long postId) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return new ResponseEntity<>(postService.savePost(postId, user.getUsername()), HttpStatus.OK);
		} catch (InvalidDataException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER') || hasRole('ROLE_AGENT')")
	@GetMapping(value = "/allSaved")
	public ResponseEntity<?> savedPosts() {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return new ResponseEntity<>(postService.savedPosts(user.getUsername()), HttpStatus.OK);
		} catch (InvalidDataException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER') || hasRole('ROLE_AGENT')")
	@GetMapping(value = "/report/{postId}")
	public ResponseEntity<?> reportPost(@PathVariable Long postId) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return new ResponseEntity<>(postService.reportPost(postId, user.getUsername()), HttpStatus.OK);
		} catch (InvalidDataException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER') || hasRole('ROLE_AGENT')")
	@PostMapping(value = "/comment")
	public ResponseEntity<?> commentPost(@RequestBody CommentDTO commentDTO) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return new ResponseEntity<>(postService.commentPost(commentDTO, user.getUsername()), HttpStatus.OK);
		} catch (InvalidDataException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/reported")
	public ResponseEntity<?> getAllReportedPosts() {
		try {
			return new ResponseEntity<>(postService.reportedPosts(), HttpStatus.OK);
		} catch (InvalidDataException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/search/{username}")
	public ResponseEntity<?> search(@PathVariable String username) {
		try {
			return new ResponseEntity<>(postService.search(username), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/remove/{postId}")
	public ResponseEntity<?> removePost(@PathVariable Long postId) {
		try {
			return new ResponseEntity<>(postService.removePost(postId), HttpStatus.OK);
		} catch (InvalidDataException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
