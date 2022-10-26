package storage;

import java.util.List;
import java.util.Map;

import help.HelperException;
import pojo.AccountInfo;
import pojo.CustomerInfo;
import pojo.TransactionInfo;
import pojo.UserInfo;

public interface StorageLayer {
	
	boolean openResource() throws HelperException;
	UserInfo validateUser(UserInfo user) throws HelperException;
	
	UserInfo getDetails(UserInfo user) throws HelperException;
	CustomerInfo getUserDetails(UserInfo user) throws HelperException;
	Map<Integer,CustomerInfo> getAllUserDetails() throws HelperException;
//	boolean changePassword(UserInfo user) throws HelperException;
	
	AccountInfo getAccountDetails(AccountInfo account) throws HelperException;
	double getTotalBalance(UserInfo user)throws HelperException;
	Map<Long,AccountInfo> getAccountDetails(UserInfo user) throws HelperException;
	Map<Integer,Map<Long,AccountInfo>> getAllAccountDetails() throws HelperException;
	List<TransactionInfo> getTransactionDetails(AccountInfo account) throws HelperException;
	List<TransactionInfo> getAllTransactionDetailsWithDate(int days)throws HelperException;
	List<TransactionInfo> getAllTransactionDetails()throws HelperException;
	List<TransactionInfo> getSingleUserTransactionDetails(UserInfo user)throws HelperException;
	
	boolean makeTransaction(TransactionInfo transaction)throws HelperException;
	
	boolean setUserStatus(CustomerInfo customer) throws HelperException;
	boolean setAccountStatus(AccountInfo account) throws HelperException;
	boolean closeResource() throws HelperException;
}
 