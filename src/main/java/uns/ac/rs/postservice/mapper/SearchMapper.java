package uns.ac.rs.postservice.mapper;

import java.util.List;

import uns.ac.rs.postservice.domain.User;
import uns.ac.rs.postservice.dto.PostDTO;
import uns.ac.rs.postservice.dto.SearchDTO;
import uns.ac.rs.postservice.dto.UserSearchDTO;

public class SearchMapper {
	
	
	public static SearchDTO fromEntity(User user, Boolean isFollowing, List<PostDTO> userPosts, List<PostDTO> taggedPosts) {
		SearchDTO dto = new SearchDTO();
		dto.setUser(new UserSearchDTO(user.getUsername(), user.getFirstName(), user.getLastName(), user.getWebsiteUrl(), user.getSex(), user.getBirthDate(), user.getBiography(), isFollowing));
		dto.setPostedPhotos(userPosts);
		dto.setTaggedPhotos(taggedPosts);
		return dto;
	}
}
