package uns.ac.rs.postservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import uns.ac.rs.postservice.domain.Post;
import uns.ac.rs.postservice.domain.User;
import uns.ac.rs.postservice.dto.PostDTO;
import uns.ac.rs.postservice.kafka.Producer;
import uns.ac.rs.postservice.mapper.PostMapper;
import uns.ac.rs.postservice.repository.PostRepository;
import uns.ac.rs.postservice.repository.UserRepository;
import uns.ac.rs.postservice.util.InvalidDataException;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private Producer producer;

	public PostDTO createPost(PostDTO postDTO, String username) throws InvalidDataException {
		
		if (postDTO.getImage().isEmpty() || postDTO.getImage() == null ) {
			throw new InvalidDataException("Invalid photo.");
		}
		Post newPost = PostMapper.toEntity(postDTO);
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new InvalidDataException("Invalid user.");
		}
		newPost.setUser(user);
		
		for (String usn : postDTO.getTaggedUsers()) {
			User taggedUser = userRepository.findByUsername(usn);
			newPost.getTaggedUsers().add(taggedUser);
		}
		postRepository.save(newPost);
		return PostMapper.fromEntity(newPost, user);
	}

	public List<Post> getAll() {
		return postRepository.findAll();
	}

	public List<PostDTO> getAllByUser(String username) {
		User user = userRepository.findByUsername(username);
		List<Post> posts = postRepository.findAllByUserId(user.getId());
		List<PostDTO> postsDTO = PostMapper.fromEntityList(posts, user);
		return postsDTO;
	}

	public List<PostDTO> getAllByPublicUsers() {
		List<Post> posts = postRepository.findAllPostsByPublicUsers();
		List<PostDTO> postsDTO = PostMapper.fromEntityListNoUser(posts);
		return postsDTO;
	}

	public List<PostDTO> getAllPostsToView(String username) throws InvalidDataException, JsonMappingException, JsonProcessingException, InterruptedException, ExecutionException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new InvalidDataException("Invalid user.");
		}
		
		List<User> followingUsers = producer.getFollowing(username);
		List<Long> ids = new ArrayList<Long>();
		for (User u : followingUsers) {
			ids.add(u.getId());
		}
		if (user.getIsPrivate()) {
			ids.add(user.getId());
		}
		List<Post> posts = postRepository.findAllByFollowingUsers(ids);
		
		
		return PostMapper.fromEntityList(posts, user);
	}

	public PostDTO likePost(Long postId, String username) throws InvalidDataException{
		Optional<Post> getPost = postRepository.findById(postId);
		if (!getPost.isPresent()) {
			throw new InvalidDataException("Post does not exist");
		}
		Post post = getPost.get();
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new InvalidDataException("Invalid user.");
		}
		post.getLikedBy().add(user);
		postRepository.save(post);
		
		return PostMapper.fromEntity(post, user);
	}
	
	public PostDTO dislikePost(Long postId, String username) throws InvalidDataException{
		Post post = postRepository.findById(postId).get();
		if (post == null) {
			throw new InvalidDataException("User does not exist");
		}
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new InvalidDataException("Invalid user.");
		}
		post.getDislikedBy().add(user);
		postRepository.save(post);
		return PostMapper.fromEntity(post, user);
	}

	public List<PostDTO> allLikedAndDislikedPosts(String username) throws InvalidDataException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new InvalidDataException("Invalid user.");
		}
		List<Post> posts = postRepository.findAllLikedOrDisliked(user.getId());
		return PostMapper.fromEntityList(posts, user);
	}

	public PostDTO undoLikePost(Long postId, String username) throws InvalidDataException{
		Optional<Post> getPost = postRepository.findById(postId);
		if (!getPost.isPresent()) {
			throw new InvalidDataException("Post does not exist");
		}
		Post post = getPost.get();
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new InvalidDataException("Invalid user.");
		}
		post.getLikedBy().remove(user);
		postRepository.save(post);
		
		return PostMapper.fromEntity(post, user);
	}
	
	public PostDTO undoDislikePost(Long postId, String username) throws InvalidDataException{
		Optional<Post> getPost = postRepository.findById(postId);
		if (!getPost.isPresent()) {
			throw new InvalidDataException("Post does not exist");
		}
		Post post = getPost.get();
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new InvalidDataException("Invalid user.");
		}
		post.getDislikedBy().remove(user);
		postRepository.save(post);
		
		return PostMapper.fromEntity(post, user);
	}

	public PostDTO savePost(Long postId, String username) throws InvalidDataException {
		Optional<Post> getPost = postRepository.findById(postId);
		if (!getPost.isPresent()) {
			throw new InvalidDataException("Post does not exist");
		}
		Post post = getPost.get();
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new InvalidDataException("Invalid user.");
		}
		post.getSavedBy().add(user);
		postRepository.save(post);
		
		return PostMapper.fromEntity(post, user);
	}

	public List<PostDTO> savedPosts(String username) throws InvalidDataException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new InvalidDataException("Invalid user.");
		}
		List<Post> posts = postRepository.findAllSaved(user.getId());
		return PostMapper.fromEntityList(posts, user);
	}

	public PostDTO reportPost(Long postId, String username) throws InvalidDataException {
		Optional<Post> getPost = postRepository.findById(postId);
		if (!getPost.isPresent()) {
			throw new InvalidDataException("Post does not exist");
		}
		Post post = getPost.get();
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new InvalidDataException("Invalid user.");
		}
		post.getReportedBy().add(user);
		postRepository.save(post);
		
		return PostMapper.fromEntity(post, user);
	}
}
