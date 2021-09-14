package uns.ac.rs.postservice.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import uns.ac.rs.postservice.domain.Post;
import uns.ac.rs.postservice.dto.CommentDTO;
import uns.ac.rs.postservice.dto.PostDTO;
import uns.ac.rs.postservice.dto.SearchDTO;
import uns.ac.rs.postservice.kafka.domain.UsersFollowBlockMute;
import uns.ac.rs.postservice.repository.PostRepository;
import uns.ac.rs.postservice.service.PostService;
import uns.ac.rs.postservice.util.InvalidDataException;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class PostIT {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private PostRepository postRepository;
	
	@Test
	@Transactional
	@Order(1)
	public void createPost_successfully() throws Exception {
		PostDTO postDTO = new PostDTO();
		postDTO.setDescription("Description");
		postDTO.setImage("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAeAB4AAD/");
		List<String> taggedUsers = new ArrayList<String>();
		postDTO.setTaggedUsers(taggedUsers);
		PostDTO result = postService.createPost(postDTO, "jova");
		assertNotNull(result.getImage());
		assertEquals(postDTO.getDescription(), result.getDescription());
	}
	
	@Test(expected = InvalidDataException.class)
	@Transactional
	@Order(2)
	public void createPost_withoutPicture() throws Exception {
		PostDTO postDTO = new PostDTO();
		postDTO.setDescription("Description");
		postDTO.setImage("");
		List<String> taggedUsers = new ArrayList<String>();
		postDTO.setTaggedUsers(taggedUsers);
		postService.createPost(postDTO, "jova");
	}
	
	@Test
	@Transactional
	@Order(3)
	public void likePost_successfully() throws Exception {
		Long postId = 1L;
		Long expected =2L;
		String username = "marko";
		PostDTO postDTO = postService.likePost(postId, username);
		assertEquals(expected, postDTO.getLikes());
	}
	
	@Test(expected = InvalidDataException.class)
	@Transactional
	@Order(4)
	public void likePost_invalidPost() throws Exception {
		Long postId = 10L;
		String username = "marko";
		postService.likePost(postId, username);
	}
	
	@Test
	@Transactional
	@Order(5)
	public void likedDislikedPosts_size() throws Exception {
		String username1 = "jova";
		String username2 = "marko";
		List<PostDTO> postsDTO = postService.allLikedAndDislikedPosts(username1);
		List<PostDTO> postsDTO2 = postService.allLikedAndDislikedPosts(username2);
		assertEquals(1, postsDTO.size());
		assertEquals(0, postsDTO2.size());
	}
	
	@Test
	@Transactional
	@Order(6)
	public void comments_size() throws Exception {
		String username = "marko";
		CommentDTO commDTO = new CommentDTO(1L, username, "text");
		PostDTO postDTO = postService.commentPost(commDTO, username);
		assertEquals(1, postDTO.getComments().size());
	}
	
	@Test
	@Transactional
	@Order(7)
	public void removeInappropriatePost_successfully() throws Exception {
		Long postId = 1L;
		Long id= postService.removePost(postId);
		assertEquals(postId, id);
		List<Post> posts = postRepository.findAll();
		assertEquals(0, posts.size());
	}
	
	@Test(expected = InvalidDataException.class)
	@Transactional
	@Order(8)
	public void removeInappropriatePost_wrongId() throws Exception {
		Long postId = 10L;
		postService.removePost(postId);
	}
	
	@Test
	@Transactional
	@Order(9)
	public void search_successfully() throws Exception {
		String usernameToSearch = "unknown";
		SearchDTO search= postService.search(usernameToSearch);
		assertNull(search);
	}
}
