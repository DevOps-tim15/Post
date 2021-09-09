package uns.ac.rs.postservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
		return PostMapper.fromEntity(newPost);
	}

	public List<Post> getAll() {
		return postRepository.findAll();
	}

	public List<PostDTO> getAllByUser() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Post> posts = postRepository.findAllByUser(user);
		List<PostDTO> postsDTO = PostMapper.fromEntityList(posts);
		return postsDTO;
	}

	public List<PostDTO> getAllByPublicUsers() {
		List<Post> posts = postRepository.findAllPostsByPublicUsers();
		List<PostDTO> postsDTO = PostMapper.fromEntityList(posts);
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
		List<Post> posts = postRepository.findAllByFollowingUsers(ids);
		if (user.getIsPrivate()) {
			posts.addAll(user.getPosts());
		}
		
		return PostMapper.fromEntityList(posts);
	}

}
