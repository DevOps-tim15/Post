package uns.ac.rs.postservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import uns.ac.rs.postservice.domain.Authority;
import uns.ac.rs.postservice.domain.Post;
import uns.ac.rs.postservice.domain.User;
import uns.ac.rs.postservice.domain.UserType;
import uns.ac.rs.postservice.kafka.Producer;
import uns.ac.rs.postservice.repository.AuthorityRepository;
import uns.ac.rs.postservice.repository.PostRepository;
import uns.ac.rs.postservice.repository.UserRepository;
import uns.ac.rs.postservice.util.InvalidDataException;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private Producer producer;

	@Autowired
	private PostRepository postRepository;

	public User saveRegisteredUser(User user) throws InvalidDataException {
		System.out.println(user);
		
		User u = findByUsername(user.getUsername());
		if(u != null) {
			throw new InvalidDataException("Username already taken!"); 
		}
		
		if(findUserById(user.getId()).isPresent()) {
			throw new InvalidDataException("Wrong ID!"); 
		}
		
		u = findByEmail(user.getEmail());
		if(u != null) {
			throw new InvalidDataException("Email already taken!"); 
		}
		
		if(Stream.of(user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword()).anyMatch(Objects::isNull)) {
			throw new InvalidDataException("Some data is missing");
		}
		
		if (user.getUsername().isEmpty() || user.getFirstName().isEmpty() || user.getLastName().isEmpty() || user.getEmail().isEmpty()
				|| user.getPassword().isEmpty()) {
			throw new InvalidDataException("User information is incomplete!");
		}
		List<Authority> authorities = new ArrayList<Authority>();
		Authority a = findAuthority(1);
		authorities.add(a);
		user.setAuthorities(authorities);
		this.userRepository.save(user);
		return user;
	}
	
	public User updateUser(User ru, String oldUsername, UserType role) throws InvalidDataException{
		User user = findByUsername(oldUsername);
		User u = findByUsername(ru.getUsername());
		if(u != null && (!ru.getUsername().equals(user.getUsername()))) {
			throw new InvalidDataException("Username already taken!"); 
		}
		u = findByEmail(ru.getEmail());
		if(u != null && (!ru.getEmail().equals(user.getEmail()))) {
			throw new InvalidDataException("Email already taken!"); 
		}
		
		if(Stream.of(ru.getUsername(), ru.getFirstName(), ru.getLastName(), ru.getEmail(), ru.getPassword()).anyMatch(Objects::isNull)) {
			throw new InvalidDataException("Some data is missing");
		}
		
		if (ru.getUsername().isEmpty() || ru.getFirstName().isEmpty() || ru.getLastName().isEmpty() || ru.getEmail().isEmpty()
				|| ru.getPassword().isEmpty()) {
			throw new InvalidDataException("User information is incomplete!");
		}
		
		if (ru.getUsername().isEmpty() || ru.getFirstName().isEmpty() || ru.getLastName().isEmpty() || ru.getEmail().isEmpty()
				|| ru.getPassword().isEmpty()) {
			throw new InvalidDataException("User information is incomplete!");
		}

		user.setUsername(ru.getUsername());
		user.setFirstName(ru.getLastName());
		user.setLastName(ru.getLastName());
		user.setEmail(ru.getEmail());
		user.setPhone(ru.getPhone());
		user.setSex(ru.getSex());
		user.setWebsiteUrl(ru.getWebsiteUrl());
		user.setBiography(ru.getBiography());
		user.setBirthDate(ru.getBirthDate());
		user.setIsPrivate(ru.getIsPrivate());
		user.setCanBeTagged(ru.getCanBeTagged());
		Authority a = this.authorityRepository.findAuthorityByUserType(role);
		List<Authority> authorities = new ArrayList<>();
		authorities.add(a);
		user.setAuthorities(authorities);
		user = this.userRepository.save(user);
		SecurityContextHolder.clearContext();
		return u;
	}
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public Optional<User> findUserById(Long id) {
		return userRepository.findById(id);
	}
	
	public Authority findAuthority(Integer id) {
		return authorityRepository.findById(id).get();
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails u =  userRepository.findByUsername(username);
		if(u!= null)
			return u;
		else
			throw new UsernameNotFoundException(String.format("User with username '%s' not found", username));
	}

	public List<String> getAllUsersForTagging() throws InvalidDataException{
		List<User> users = userRepository.findByCanBeTaggedTrue();
		List<String> usernames = new ArrayList<String>();
		for (User user : users) {
			System.out.println(user.getUsername());
			usernames.add(user.getUsername());
//			if (containsName(user.getAuthorities(), UserType.ROLE_REGISTERED_USER) || containsName(user.getAuthorities(), UserType.ROLE_AGENT)) {
//				usernames.add(user.getUsername());
//			}
		}
		return usernames;
	}
	
	public List<String> getAllUsersForSearch() throws InvalidDataException, JsonMappingException, JsonProcessingException, InterruptedException, ExecutionException{
		List<User> users = userRepository.findAllRegisteredUsers();
		List<String> usernames = new ArrayList<String>();
		String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		for (User user : users) {
			System.out.println(user.getUsername());
			usernames.add(user.getUsername());
		}
		if(!loggedUsername.equals("anonymousUser")) {
			if(usernames.contains(loggedUsername)) {
				usernames.remove(loggedUsername);
			}
		}
		return usernames;
	}
	public boolean containsName(List<Authority> list, UserType userType){
	    return list.stream().anyMatch(o -> o.getUserType().equals(userType));
	}

	public void deleteUser(User user) {
		User u = userRepository.findByUsername(user.getUsername());
		userRepository.delete(u);
		List<Post> posts = postRepository.findAllByUserId(user.getId());
		for (Post post : posts) {
			postRepository.delete(post);
		}
		List<Post> postsTaggedByUser = postRepository.getAllTaggedByUser(u.getId());
		for (Post post : postsTaggedByUser) {
			post.getTaggedUsers().remove(u);
			postRepository.save(post);
		}
		
	}

}
