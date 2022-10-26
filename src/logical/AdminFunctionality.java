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

public class AdminFunctionality {
	
	UserInfo userDetails=new UserInfo();
	StorageLayer storage=new DataProcess();
	
	public AdminFunctionality(UserInfo user) {
		this.userDetails=user;
	}
	
	public UserInfo getOwnProfile()throws HelperException{
		return storage.getDetails(userDetails);
	}
	
	public CustomerInfo getProfile() throws HelperException {
		return storage.getUserDetails(userDetails);
	}
	
	public CustomerInfo viewCustomerProfile(int userId) throws HelperException{
		UserInfo user=new UserInfo();
		user.setUserId(userId);
		return storage.getUserDetails(user);
	}
	
	public Map<Integer,Map<Long,AccountInfo>> viewAllAccount()throws HelperException {
		return storage.getAllAccountDetails();
	}
	
	public Map<Integer,CustomerInfo> viewAllUserDetails()throws HelperException {
		return storage.getAllUserDetails();
	}
	
	public List<TransactionInfo> viewAllTransaction()throws HelperException {
		return storage.getAllTransactionDetails();
	}
	
	public List<TransactionInfo> viewTransaction(int userid) throws HelperException {
		UserInfo user=new UserInfo();
		user.setUserId(userid);
		return storage.getSingleUserTransactionDetails(user);
	}
	
	public List<TransactionInfo> viewTransaction(Long accountNumber) throws HelperException {
		AccountInfo account=new AccountInfo();
		account.setAccountNumber(accountNumber);
		return storage.getTransactionDetails(account);
	}
	
	public List<TransactionInfo> getTransactionDetailswithDate(int day) throws HelperException{
		return storage.getAllTransactionDetailsWithDate(day);
	}
	
	public boolean changeUserStatus(int userId,String status)throws HelperException {
		CustomerInfo customer=new CustomerInfo();
		customer.setUserId(userId);
		customer.setUserStatus(status);
		return storage.setUserStatus(customer);
	}
	
	public boolean changeAccountStatus(Long accountNumber,String status)throws HelperException {
		AccountInfo account=new AccountInfo();
		account.setAccountNumber(accountNumber);
		account.setAccountStatus(status);
		return storage.setAccountStatus(account);
	}
	
}
