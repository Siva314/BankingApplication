package pojo;

public class TransactionInfo {
	private Long transactionId,accountNumber,senderAccountNumber,
			receiverAccountNumber,transactionDate;
	private int userId;
	private double transactionAmount,currentBalance;
	private String transactionType,referenceId;
	
	public Long getTransactionId() {
		return transactionId;
	}
	public Long getAccountNumber() {
		return accountNumber;
	}
	public Long getSenderAccountNumber() {
		return senderAccountNumber;
	}
	public Long getReceiverAccountNumber() {
		return receiverAccountNumber;
	}
	public int getUserId() {
		return userId;
	}
	public Long getTransactionDate() {
		return transactionDate;
	}
	public double getTransactionAmount() {
		return transactionAmount;
	}
	public double getCurrentBalance() {
		return currentBalance;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public String getReferenceId() {
		return referenceId;
	}
	
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public void setSenderAccountNumber(Long senderAccountNumber) {
		this.senderAccountNumber = senderAccountNumber;
	}
	public void setReceiverAccountNumber(Long receiverAccountNumber) {
		this.receiverAccountNumber = receiverAccountNumber;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public void setTransactionDate(Long transactionDate) {
		this.transactionDate = transactionDate;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
}