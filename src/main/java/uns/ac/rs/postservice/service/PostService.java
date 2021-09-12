package uns.ac.rs.postservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import uns.ac.rs.postservice.domain.Comment;
import uns.ac.rs.postservice.domain.Post;
import uns.ac.rs.postservice.domain.User;
import uns.ac.rs.postservice.dto.CommentDTO;
import uns.ac.rs.postservice.dto.PostDTO;
import uns.ac.rs.postservice.dto.SearchDTO;
import uns.ac.rs.postservice.kafka.Producer;
import uns.ac.rs.postservice.kafka.domain.UsersFollowBlockMute;
import uns.ac.rs.postservice.mapper.PostMapper;
import uns.ac.rs.postservice.mapper.SearchMapper;
import uns.ac.rs.postservice.repository.CommentRepository;
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
	private CommentRepository commentRepository;
	
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
		
		UsersFollowBlockMute users = producer.getFollowBlockMute(username);
		List<Long> ids = new ArrayList<Long>();
		for (User u : users.getFollowing()) {
			ids.add(u.getId());
		}
		for (User u: users.getBlock()) {
			if(ids.contains(u.getId())) {
				ids.remove(u.getId());
			}
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

	public PostDTO commentPost(CommentDTO commentDTO, String username) throws InvalidDataException {
		Optional<Post> getPost = postRepository.findById(commentDTO.getPostId());
		if (!getPost.isPresent()) {
			throw new InvalidDataException("Post does not exist");
		}
		Post post = getPost.get();
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new InvalidDataException("Invalid user.");
		}
		
		Comment comment = new Comment();
		comment.setPost(post);
		comment.setText(commentDTO.getText());
		comment.setUser(user);
		commentRepository.save(comment);
		return PostMapper.fromEntity(post, user);
	}

	public List<PostDTO> reportedPosts() throws InvalidDataException{
		Set<Post> posts = postRepository.findAllReported();
		return PostMapper.fromEntityListNoUser(new ArrayList<>(posts));
	}
	
	public SearchDTO search(String username) throws InvalidDataException, JsonMappingException, JsonProcessingException, InterruptedException, ExecutionException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			return null;
		}
		String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		Boolean following = false;
		Boolean isOwner = false;
		if(!loggedUsername.contentEquals("anonymousUser")) {
			if(username.equals(loggedUsername)) {
				isOwner = true;	
			}
			else {
				UsersFollowBlockMute userFBM = producer.getFollowBlockMute(loggedUsername);
				System.out.println(userFBM.getBlock());
				for(User u: userFBM.getBlock()) {
					System.out.println(u.getUsername());
					if(u.getUsername().equals(username)) {
						return null;
					}
				}
				for(User u : userFBM.getFollowing()) {
					if(u.getUsername().equals(username)) {
						following = true;
						break;
					}
				}
			}
			
			if((!isOwner) && (!following) && (user.getIsPrivate())) {
				return null;
			}
		}else {
			if(user.getIsPrivate()) {
				return null;
			}
		}
		List<PostDTO> userPosts = this.getAllByUser(username);
		List<PostDTO> taggedPosts = this.getAllTagged(username);
		SearchDTO dto = SearchMapper.fromEntity(user, following,  isOwner, userPosts, taggedPosts);
		return dto;
	}
	
	public List<PostDTO> getAllTagged(String username) {
		User user = userRepository.findByUsername(username);
		Set<Post> posts = postRepository.findAllTagged(user.getId());
		return PostMapper.fromEntityListNoUser(new ArrayList<>(posts));
	}

	public Long removePost(Long postId) throws InvalidDataException{
		Optional<Post> getPost = postRepository.findById(postId);
		if (!getPost.isPresent()) {
			throw new InvalidDataException("Wrong post id!");
		}
		Post post = getPost.get();
		postRepository.delete(post);
		return postId;
	}
}
