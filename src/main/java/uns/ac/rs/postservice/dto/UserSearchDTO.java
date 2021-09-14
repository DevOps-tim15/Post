package uns.ac.rs.postservice.dto;

public class UserSearchDTO {

	private String username;
	private String firstName;
	private String lastName;
	private String websiteUrl;
	private String sex;
	private String birthDate;
	private String biography;
	private Boolean isFollowing;
	private Boolean isPrivate;
	private Boolean isOwner;
	private Boolean isMuted;
	private Boolean isRequested;
	
	public UserSearchDTO() {
		super();
	}
	public UserSearchDTO(String username, String firstName, String lastName, String websiteUrl, String sex,
			String birthDate, String biography, Boolean isFollowing, Boolean isPrivate, Boolean isOwner, Boolean isMuted, Boolean isRequested) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.websiteUrl = websiteUrl;
		this.sex = sex;
		this.birthDate = birthDate;
		this.biography = biography;
		this.isFollowing = isFollowing;
		this.isPrivate = isPrivate;
		this.isOwner = isOwner;
		this.isMuted = isMuted;
		this.isRequested = isRequested;
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
	public Boolean getIsPrivate() {
		return isPrivate;
	}
	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	public Boolean getIsOwner() {
		return isOwner;
	}
	public void setIsOwner(Boolean isOwner) {
		this.isOwner = isOwner;
	}
	public Boolean getIsMuted() {
		return isMuted;
	}
	public void setIsMuted(Boolean isMuted) {
		this.isMuted = isMuted;
	}
	public Boolean getIsRequested() {
		return isRequested;
	}
	public void setIsRequested(Boolean isRequested) {
		this.isRequested = isRequested;
	}
}
