package uns.ac.rs.postservice.mapper;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import uns.ac.rs.postservice.domain.Post;
import uns.ac.rs.postservice.domain.User;
import uns.ac.rs.postservice.dto.PostDTO;

public class PostMapper {
	
	public static PostDTO fromEntity(Post post, User user) {
		PostDTO postDTO = new PostDTO(post.getId(), post.getUser().getUsername(), post.getDescription(), new String(post.getPicture(),  StandardCharsets.UTF_8), UserMapper.fromEntityToString(post.getTaggedUsers()));
		postDTO.setLikes((long) post.getLikedBy().size());
		postDTO.setDislikes((long) post.getDislikedBy().size());
		if (user.getLikedPosts().stream().anyMatch(el -> el.getId() == post.getId())) {
			postDTO.setCanBeLiked(false);
		} else {
			postDTO.setCanBeLiked(true);
		}
		
		if (user.getDislikedPosts().stream().anyMatch(el -> el.getId() == post.getId())) {
			postDTO.setCanBeDisliked(false);
		} else {
			postDTO.setCanBeDisliked(true);
		}
		
		if (user.getSavedPosts().stream().anyMatch(el -> el.getId() == post.getId())) {
			postDTO.setCanBeSaved(false);
		} else {
			postDTO.setCanBeSaved(true);
		}
		
		if (user.getReportedPosts().stream().anyMatch(el -> el.getId() == post.getId())) {
			postDTO.setCanBeReported(false);
		} else {
			postDTO.setCanBeReported(true);
		}
		return postDTO;
	}
	public static List<PostDTO> fromEntityListNoUser(List<Post> posts) {
		List<PostDTO> postsDTO = new ArrayList<>();
		for (Post post : posts) {
			PostDTO postDTO = new PostDTO(post.getId(), post.getUser().getUsername(), post.getDescription(), new String(post.getPicture(),  StandardCharsets.UTF_8), UserMapper.fromEntityToString(post.getTaggedUsers()));
			postDTO.setLikes((long) post.getLikedBy().size());
			postDTO.setDislikes((long) post.getDislikedBy().size());
			postDTO.setCanBeLiked(false);
			postDTO.setCanBeDisliked(false);
			postDTO.setCanBeSaved(false);
			postDTO.setCanBeReported(false);
			postsDTO.add(postDTO);
		}
		
		return postsDTO;
	}
	
	
	public static Post toEntity(PostDTO postDTO) {
		Post post = new Post();
		post.setDescription(postDTO.getDescription());
		post.setPicture(postDTO.getImage().getBytes(StandardCharsets.UTF_8));
		post.setTaggedUsers(new ArrayList<User>());
		return post;
	}
	
	public static List<PostDTO> fromEntityList(List<Post> posts, User user) {
		List<PostDTO> postsDTO = new ArrayList<>();
		for (Post post : posts) {
			postsDTO.add(fromEntity(post, user));
		}
		return postsDTO;
	}
}
