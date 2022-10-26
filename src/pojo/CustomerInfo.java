package pojo;

public class CustomerInfo extends UserInfo {
	private int userId;
	private Long aadhaar;
	private String userStatus,pancard;
	
	
	public Long getAadhaar() {
		return aadhaar;
	}
	public String getPancard() {
		return pancard;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public int getUserId() {
		return userId;
	}
	
	public void setAadhaar(Long aadhaar) {
		this.aadhaar = aadhaar;
	}
	public void setPancard(String pancard) {
		this.pancard = pancard;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
