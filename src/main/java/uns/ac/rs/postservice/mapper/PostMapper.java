package uns.ac.rs.postservice.mapper;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import uns.ac.rs.postservice.domain.Post;
import uns.ac.rs.postservice.domain.User;
import uns.ac.rs.postservice.dto.PostDTO;

public class PostMapper {
	
	public static PostDTO fromEntity(Post post) {
		return new PostDTO(post.getDescription(), new String(post.getPicture(),  StandardCharsets.UTF_8), UserMapper.fromEntityToString(post.getTaggedUsers()));
	}
	
	public static Post toEntity(PostDTO postDTO) {
		Post post = new Post();
		post.setDescription(postDTO.getDescription());
		post.setPicture(postDTO.getImage().getBytes(StandardCharsets.UTF_8));
		post.setTaggedUsers(new ArrayList<User>());
//		post.setTaggedUsers(UserMapper.toEntityList(postDTO.getTaggedUsers()));
		return post;
	}
	
	public static List<PostDTO> fromEntityList(List<Post> posts) {
		List<PostDTO> postsDTO = new ArrayList<>();
		for (Post post : posts) {
			postsDTO.add(fromEntity(post));
		}
		return postsDTO;
	}
}
