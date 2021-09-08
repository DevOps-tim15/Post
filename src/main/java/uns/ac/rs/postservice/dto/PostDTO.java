package uns.ac.rs.postservice.dto;

import java.util.List;

public class PostDTO {
	
	private String description;
	private String image;
	private List<String> taggedUsers;
	
	public PostDTO() {
		super();
	}
	
	public PostDTO(String description, String image, List<String> taggedUsers) {
		super();
		this.description = description;
		this.image = image;
		this.taggedUsers = taggedUsers;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<String> getTaggedUsers() {
		return taggedUsers;
	}

	public void setTaggedUsers(List<String> taggedUsers) {
		this.taggedUsers = taggedUsers;
	}
	
}
