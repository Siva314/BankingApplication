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

public class BaseFunction implements AdminFunctionality,UserFunctionality{
	
	StorageLayer storage=new DataProcess();
	
	public String checkLogin(int id,String password) throws HelperException {
		UserInfo user=new UserInfo();
		user.setUserId(id);
		user.setPassword(password);
		UserInfo temp=storage.validateUser(user);
		return temp.getType();
	}

//	@Override
	public AccountInfo viewAccount(Long accountnumber) throws HelperException {
		AccountInfo account=new AccountInfo();
		account.setAccountNumber(accountnumber);
		return storage.getAccountDetails(account);
	}

	@Override
	public Map<Integer, Map<Long, AccountInfo>> viewAllAccount() throws HelperException {
		return storage.getAllAccountDetails();
	}

	@Override
	public Map<Integer, CustomerInfo> viewAllUserDetails() throws HelperException {
		return storage.getAllUserDetails();
	}

	@Override
	public List<TransactionInfo> viewAllTransaction() throws HelperException {
		return storage.getAllTransactionDetails();
	}

	@Override
	public String changeUserStatus(int userId,String status) throws HelperException {
		CustomerInfo customer=new CustomerInfo();
		customer.setUserId(userId);
		customer.setUserStatus(status);
		if(storage.setUserStatus(customer)) {
			return "Status changed successfully";
		}
		else {
			return "Status didn't updated";
		}
	}

	@Override
	public String changeAccountStatus(Long accountNumber,String status) throws HelperException {
		AccountInfo account=new AccountInfo();
		account.setAccountNumber(accountNumber);
		account.setAccountStatus(status);
		if(storage.setAccountStatus(account)) {
			return "Status changed successfully";
		}
		else {
			return "Status didn't updated";
		}
	}


	@Override
	public double viewTotalAccountBalance(int userId) throws HelperException {
		UserInfo user=new UserInfo();
		user.setUserId(userId);
		return storage.getTotalBalance(user);
	}

	@Override
	public String deposit(double amount) throws HelperException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String withdraw(double amount) throws HelperException {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public String transfer(Long senderAccount, double amount) throws HelperException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerInfo viewProfile(int userId) throws HelperException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TransactionInfo> viewTransaction(int userid) throws HelperException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TransactionInfo> viewTransaction(Long accountNumber) throws HelperException {
		// TODO Auto-generated method stub
		return null;
	}
}
