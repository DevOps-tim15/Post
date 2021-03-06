package uns.ac.rs.postservice.dto;

import java.util.ArrayList;
import java.util.List;

public class PostDTO {
	
	private Long postId;
	private String username;
	private String description;
	private String image;
	private List<String> taggedUsers;
	private Long likes = 0L;
	private Long dislikes = 0L;
	private Boolean canBeLiked = true;
	private Boolean canBeDisliked = true;
	private Boolean canBeSaved = true;
	private Boolean canBeReported = true;
	private List<CommentDTO> comments = new ArrayList<CommentDTO>();
	private List<String> reportedBy = new ArrayList<String>();
	
	public PostDTO() {
		super();
	}
	
	public PostDTO(Long id, String username, String description, String image, List<String> taggedUsers) {
		super();
		this.postId = id;
		this.username = username;
		this.description = description;
		this.image = image;
		this.taggedUsers = taggedUsers;
	}
	
	public PostDTO(Long postId, String username, String description, String image, List<String> taggedUsers, Long likes, Long dislikes, 
			Boolean canBeLiked, Boolean canBeDisliked, Boolean canBeSaved, Boolean canBeReported, List<CommentDTO> comments, List<String> reportedBy) {

		super();
		this.postId = postId;
		this.username = username;
		this.description = description;
		this.image = image;
		this.taggedUsers = taggedUsers;
		this.likes = likes;
		this.dislikes = dislikes;
		this.canBeLiked = canBeLiked;
		this.canBeDisliked = canBeDisliked;
		this.canBeSaved = canBeSaved;
		this.canBeReported = canBeReported;
		this.comments = comments;
		this.reportedBy = reportedBy;
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

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}

	public Long getDislikes() {
		return dislikes;
	}

	public void setDislikes(Long dislikes) {
		this.dislikes = dislikes;
	}

	public Boolean getCanBeLiked() {
		return canBeLiked;
	}

	public void setCanBeLiked(Boolean canBeLiked) {
		this.canBeLiked = canBeLiked;
	}

	public Boolean getCanBeDisliked() {
		return canBeDisliked;
	}

	public void setCanBeDisliked(Boolean canBeDisliked) {
		this.canBeDisliked = canBeDisliked;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getCanBeSaved() {
		return canBeSaved;
	}

	public void setCanBeSaved(Boolean canBeSaved) {
		this.canBeSaved = canBeSaved;
	}
	public Boolean getCanBeReported() {
		return canBeReported;
	}

	public void setCanBeReported(Boolean canBeReported) {
		this.canBeReported = canBeReported;
	}

	public List<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}

	public List<String> getReportedBy() {
		return reportedBy;
	}

	public void setReportedBy(List<String> reportedBy) {
		this.reportedBy = reportedBy;
	}
}
