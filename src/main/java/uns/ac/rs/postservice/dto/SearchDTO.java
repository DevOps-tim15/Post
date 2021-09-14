package uns.ac.rs.postservice.dto;

import java.util.List;

public class SearchDTO {

	private UserSearchDTO user;
	private List<PostDTO> postedPhotos;
	private List<PostDTO> taggedPhotos;
	
	public SearchDTO() {
		super();
	}
	
	public SearchDTO(UserSearchDTO user, List<PostDTO> postedPhotos, List<PostDTO> taggedPhotos) {
		super();
		this.user = user;
		this.postedPhotos = postedPhotos;
		this.taggedPhotos = taggedPhotos;
	}

	public UserSearchDTO getUser() {
		return user;
	}
	public void setUser(UserSearchDTO user) {
		this.user = user;
	}
	public List<PostDTO> getPostedPhotos() {
		return postedPhotos;
	}
	public void setPostedPhotos(List<PostDTO> postedPhotos) {
		this.postedPhotos = postedPhotos;
	}
	public List<PostDTO> getTaggedPhotos() {
		return taggedPhotos;
	}
	public void setTaggedPhotos(List<PostDTO> taggedPhotos) {
		this.taggedPhotos = taggedPhotos;
	}
	
	
}
