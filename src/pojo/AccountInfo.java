package pojo;


public class AccountInfo {
	private int userId;	
	private Long accountNumber;
	private double accountBalance;
	private String accountType,ifscCode,branchName,accountStatus;
	
	public int getUserId() {
		return userId;
	}
	public Long getAccountNumber() {
		return accountNumber;
	}
	public double getAccountBalance() {
		return accountBalance;
	}
	public String getAccountType() {
		return accountType;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public String getBranchName() {
		return branchName;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
