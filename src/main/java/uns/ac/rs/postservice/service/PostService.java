package uns.ac.rs.postservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import uns.ac.rs.postservice.domain.Post;
import uns.ac.rs.postservice.domain.User;
import uns.ac.rs.postservice.dto.PostDTO;
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

	public Post createPost(PostDTO postDTO, String username) throws InvalidDataException {
		
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
		return newPost;
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

}
