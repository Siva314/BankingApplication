package logical;


import java.util.List;
import java.util.Map;

import help.HelperException;
import pojo.AccountInfo;
import pojo.CustomerInfo;
import pojo.TransactionInfo;
import pojo.UserInfo;
import storage.DataProcess;
import storage.StorageLayer;
import util.HelperUtils;

public class UserFunctionality {
	
	StorageLayer storage=new DataProcess();
	
	AccountInfo account;
	CustomerInfo customer;
	UserInfo user;
	TransactionInfo transaction;
	Map<Long,AccountInfo> accountDetails;
	
	public UserFunctionality(UserInfo user) {
		this.user=user;
	}
	
	public Map<Long,AccountInfo> getAccounts()throws HelperException {
		accountDetails=storage.getAccountDetails(user);
		return accountDetails;
	}
	
	public AccountInfo getAccountDetails(Long accountNumber) {
		account=accountDetails.get(accountNumber);
		return account;
	}
	
	public UserInfo getUserProfile() throws HelperException{
		user=storage.getDetails(user);
		return user;
	}
	
	public CustomerInfo getProfile() throws HelperException {
		return storage.getUserDetails(user);
	}
	
	public void setValueInTransaction() {
		transaction=new TransactionInfo();
		transaction.setUserId(user.getUserId());
		transaction.setAccountNumber(account.getAccountNumber());
		transaction.setSenderAccountNumber(account.getAccountNumber());
		transaction.setReceiverAccountNumber(account.getAccountNumber());
	}
	
//	double viewAccountBalance(Long accountNumber) throws HelperException;
	public double getTotalAccountBalance(int userId) throws HelperException {		
		return storage.getTotalBalance(user);
	}
	
	public boolean deposit(double amount) throws HelperException {
		setValueInTransaction();
		Long milliSecond=System.currentTimeMillis();
		transaction.setTransactionDate(milliSecond);
		transaction.setReferenceId(HelperUtils.getReferenceId(milliSecond));
		transaction.setTransactionAmount(amount);
		transaction.setTransactionType("deposit");
		return storage.makeTransaction(transaction);
	}
	
	public boolean withdraw(double amount) throws HelperException {
		setValueInTransaction();
		Long milliSecond=System.currentTimeMillis();
		transaction.setTransactionDate(milliSecond);
		transaction.setReferenceId(HelperUtils.getReferenceId(milliSecond));
		transaction.setTransactionAmount(amount);
		transaction.setTransactionType("withdraw");
		return storage.makeTransaction(transaction);
	}
	
	public boolean transfer(Long receiverAccount,double amount) throws HelperException {
		setValueInTransaction();
		Long milliSecond=System.currentTimeMillis();
		transaction.setTransactionDate(milliSecond);
		transaction.setReferenceId(HelperUtils.getReferenceId(milliSecond));
		transaction.setReceiverAccountNumber(receiverAccount);
		transaction.setTransactionAmount(amount);
		transaction.setTransactionType("transfer");
		return storage.makeTransaction(transaction);
	}
	
	public List<TransactionInfo> getTransactionDetails() throws HelperException {
		return storage.getTransactionDetails(account);
	}
	
	public List<TransactionInfo> getTransactionDetailswithDate(int day) throws HelperException{
		return storage.getAllTransactionDetailsWithDate(day);
	}
}
