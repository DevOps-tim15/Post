package uns.ac.rs.postservice.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="post")
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
	private User user;
	
	@Column(name = "decscription")
	private String description;
	
	@Lob
	private byte[] picture;
	
	
	@ManyToMany
	@JoinTable(name = "post_tagged_user", joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	private List<User> taggedUsers;
	
	@ManyToMany
	@JoinTable(name = "post_liked_by", joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	private List<User> likedBy;
	
	@ManyToMany
	@JoinTable(name = "post_disliked_by", joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	private List<User> dislikedBy;
	
	@ManyToMany
	@JoinTable(name = "post_saved_by", joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	private List<User> savedBy;
	
	@ManyToMany
	@JoinTable(name = "post_reported_by", joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	private List<User> reportedBy;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "post", fetch = FetchType.LAZY)
	@JsonIgnoreProperties("post")
	private List<Comment> comments;
	
	public Post() {
		super();
		this.likedBy = new ArrayList<User>();
		this.dislikedBy = new ArrayList<User>();
		this.savedBy = new ArrayList<User>();
		this.reportedBy = new ArrayList<User>();
		this.comments = new ArrayList<Comment>();
	}

	public Post(Long id, User user, String description, byte[] picture, List<User> taggedUsers) {
		super();
		this.id = id;
		this.user = user;
		this.description = description;
		this.picture = picture;
		this.taggedUsers = taggedUsers;
		this.likedBy = new ArrayList<User>();
		this.dislikedBy = new ArrayList<User>();
		this.savedBy = new ArrayList<User>(); 
		this.reportedBy = new ArrayList<User>();
		this.comments = new ArrayList<Comment>();
	}
	
	
	public Post(Long id, User user, String description, byte[] picture, List<User> taggedUsers, List<User> likedBy,
		List<User> dislikedBy, List<User> savedBy, List<User> reportedBy, List<Comment> comments) {
		super();
		this.id = id;
		this.user = user;
		this.description = description;
		this.picture = picture;
		this.taggedUsers = taggedUsers;
		this.likedBy = likedBy;
		this.dislikedBy = dislikedBy;
		this.savedBy = savedBy;
		this.reportedBy = reportedBy;
		this.comments = comments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public List<User> getTaggedUsers() {
		return taggedUsers;
	}

	public void setTaggedUsers(List<User> taggedUsers) {
		this.taggedUsers = taggedUsers;
	}

	public List<User> getLikedBy() {
		return likedBy;
	}

	public void setLikedBy(List<User> likedBy) {
		this.likedBy = likedBy;
	}

	public List<User> getDislikedBy() {
		return dislikedBy;
	}

	public void setDislikedBy(List<User> dislikedBy) {
		this.dislikedBy = dislikedBy;
	}

	public List<User> getSavedBy() {
		return savedBy;
	}

	public void setSavedBy(List<User> savedBy) {
		this.savedBy = savedBy;
	}
	public List<User> getReportedBy() {
		return reportedBy;
	}

	public void setReportedBy(List<User> reportedBy) {
		this.reportedBy = reportedBy;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
}
