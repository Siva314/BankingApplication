package pojo;

public class UserInfo {
	private int userId;
	private Long dateOfBirth,mobile;
	private String name,password,address,type,email;
	
	public String getEmail() {
		return email;
	}
	public int getUserId() {
		return userId;
	}
	public Long getMobile() {
		return mobile;
	}
	public Long getDateOfBirth() {
		return dateOfBirth;
	}
	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	public String getAddress() {
		return address;
	}
	public String getType() {
		return type;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}
	public void setDateOfBirth(Long dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}