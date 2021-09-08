package uns.ac.rs.postservice.domain;

import java.util.List;

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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
//	@JsonIgnore
	private List<User> taggedUsers;

	public Post() {
		super();
	}

	public Post(Long id, User user, String description, byte[] picture, List<User> taggedUsers) {
		super();
		this.id = id;
		this.user = user;
		this.description = description;
		this.picture = picture;
		this.taggedUsers = taggedUsers;
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
	
}
