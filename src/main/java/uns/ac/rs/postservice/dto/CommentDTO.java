package uns.ac.rs.postservice.dto;

public class CommentDTO {
	
	private Long postId;
	
	private String username;
	
	private String text;

	public CommentDTO() {
		super();
	}

	public CommentDTO(Long postId, String username, String text) {
		super();
		this.postId = postId;
		this.username = username;
		this.text = text;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
