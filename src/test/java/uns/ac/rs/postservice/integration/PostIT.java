package uns.ac.rs.postservice.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

import uns.ac.rs.postservice.dto.PostDTO;
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

}
