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
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import uns.ac.rs.postservice.domain.Post;
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
		Post result = postService.createPost(postDTO, "jova");
		assertNotNull(result.getId());
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
}
