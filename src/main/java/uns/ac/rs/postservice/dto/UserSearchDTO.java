package uns.ac.rs.postservice.dto;

import javax.persistence.Column;

public class UserSearchDTO {

	private String username;
	private String firstName;
	private String lastName;
	private String websiteUrl;
	private String sex;
	private String birthDate;
	private String biography;
	private Boolean isFollowing;
	
	public UserSearchDTO() {
		super();
	}
	public UserSearchDTO(String username, String firstName, String lastName, String websiteUrl, String sex,
			String birthDate, String biography, Boolean isFollowing) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.websiteUrl = websiteUrl;
		this.sex = sex;
		this.birthDate = birthDate;
		this.biography = biography;
		this.isFollowing = isFollowing;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getWebsiteUrl() {
		return websiteUrl;
	}
	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getBiography() {
		return biography;
	}
	public void setBiography(String biography) {
		this.biography = biography;
	}
	public Boolean getIsFollowing() {
		return isFollowing;
	}
	public void setIsFollowing(Boolean isFollowing) {
		this.isFollowing = isFollowing;
	}
}
