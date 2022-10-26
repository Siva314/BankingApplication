package storage;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import help.HelperException;
import pojo.AccountInfo;
import pojo.CustomerInfo;
import pojo.TransactionInfo;
import pojo.UserInfo;
import util.HelperUtils;

public class DataProcess implements StorageLayer {

	static Connection connection;

	@Override
	public boolean openResource() throws HelperException {
		connection=getConnection();
		return true;
	}

	private Connection getConnection() throws HelperException {
		String url = "jdbc:mysql://localhost/BankingApplication";
		String uName = "siva";
		String pass = "SIVA@314";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, uName, pass);
		} catch (SQLException e) {
			throw new HelperException(e);
		}
		return conn;
	}

	private Map<Integer, CustomerInfo> storeDetailsInMapForCustomer(ResultSet tempSet)
			throws HelperException {
		try (ResultSet resultSet = tempSet) {
			Map<Integer, CustomerInfo> innerMap = new HashMap<Integer, CustomerInfo>();
			int userId = 0;
			while (resultSet.next()) {
				CustomerInfo customer = new CustomerInfo();
				userId = resultSet.getInt("userid");
				customer.setUserId(userId);
				customer.setAadhaar(resultSet.getLong("aadhaar"));
				customer.setAddress(resultSet.getString("address"));
				customer.setDateOfBirth(resultSet.getLong("dateofBirth"));
				customer.setEmail(resultSet.getString("email"));
				customer.setMobile(resultSet.getLong("mobile"));
				customer.setName(resultSet.getString("name"));
				customer.setPancard(resultSet.getString("pancard"));
				customer.setPassword(resultSet.getString("password"));
				customer.setType(resultSet.getString("type"));
				customer.setUserStatus(resultSet.getString("user_status"));
				innerMap.put(userId, customer);
			}
			return innerMap;
		} catch (SQLException e) {
			throw new HelperException(e);
		}
	}

	private boolean updateAccountBalance(long account_number, double balance) throws HelperException {
		String query2 = "update AccountInfo set account_balance=? where account_number=?";
		try (PreparedStatement statement = connection.prepareStatement(query2)) {
			statement.setDouble(1, balance);
			statement.setLong(2, account_number);
			if (statement.executeUpdate() == 1) {
				return true;
			}
		} catch (SQLException e) {
			throw new HelperException(e);
		}
		return false;
	}

	private int getUserId(long accountNumber) throws HelperException {
		int id = 0;
		String query = "select userid from AccountInfo where account_number=?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, accountNumber);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					id = resultSet.getInt("userid");
				}
			}
		} catch (SQLException e) {
			throw new HelperException(e);
		}
		return id;
	}

	private List<TransactionInfo> setValuesInTransactionPojo(ResultSet tempSet) throws HelperException {
		List<TransactionInfo> transactionList = new ArrayList<TransactionInfo>();
		try (ResultSet resultSet = tempSet) {
			while (resultSet.next()) {
				TransactionInfo transaction = new TransactionInfo();
				Long id = resultSet.getLong("transactionid");
				transaction.setTransactionId(id);
				transaction.setAccountNumber(resultSet.getLong("account_number"));
				transaction.setCurrentBalance(resultSet.getDouble("current_balance"));
				transaction.setReceiverAccountNumber(resultSet.getLong("receiver_account_number"));
				transaction.setSenderAccountNumber(resultSet.getLong("receiver_account_number"));
				transaction.setTransactionAmount(resultSet.getDouble("transaction_amount"));
				transaction.setTransactionType(resultSet.getString("transaction_type"));
				transaction.setUserId(resultSet.getInt("userid"));
				transaction.setTransactionDate(resultSet.getLong("transaction_date"));
				transaction.setReferenceId(resultSet.getString("referenceId"));
				transactionList.add(transaction);
			}
		} catch (SQLException e) {
			throw new HelperException(e);
		}
		return transactionList;
	}

	private boolean insertIntoTransactionTable(TransactionInfo transaction, int condition) throws HelperException {
		long sender = transaction.getSenderAccountNumber(), receiver = transaction.getReceiverAccountNumber();
		String type = transaction.getTransactionType();
		double sBalance = getBalance(sender), rBalance = getBalance(receiver);
		String query = "insert into TransactionInfo (userid,account_number,transaction_date,sender_account_number,"
				+ "receiver_account_number,transaction_amount,current_balance,transaction_type,referenceId) values(?,?,?,?,?,?,?,?,?)";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, transaction.getUserId());
			statement.setLong(2, transaction.getAccountNumber());
			statement.setLong(3, transaction.getTransactionDate());
			statement.setLong(4, sender);
			statement.setLong(5, receiver);
			statement.setDouble(6, transaction.getTransactionAmount());
			statement.setDouble(7, transaction.getCurrentBalance());
			statement.setString(8, type);
			statement.setString(9, transaction.getReferenceId());
			if (statement.executeUpdate() == 1) {
				if (condition == 1) {
					if (type.equalsIgnoreCase("transfer")) {
						updateAccountBalance(sender, (sBalance - transaction.getTransactionAmount()));
						updateAccountBalance(receiver, (rBalance + transaction.getTransactionAmount()));
						return true;
					} else {
						updateAccountBalance(receiver, transaction.getCurrentBalance());
						return true;
					}
				}
			}
		} catch (SQLException e) {
			throw new HelperException(e);
		}
		return false;
	}

	@Override
	public UserInfo validateUser(UserInfo userDetails) throws HelperException {
		UserInfo user = new UserInfo();
		String role = null;
		String query = "select type from UserInfo where userid=? and password=?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, userDetails.getUserId());
			statement.setString(2, userDetails.getPassword());
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					role = resultSet.getString("type");
				}
			}

		} catch (SQLException e) {
			throw new HelperException(e);
		}
		user.setType(role);
		return user;
	}

	@Override
	public CustomerInfo getUserDetails(UserInfo userDetails) throws HelperException {
		CustomerInfo customer = new CustomerInfo();
		String query = "select * from CustomerInfo join UserInfo on CustomerInfo.userid=UserInfo.userid where CustomerInfo.userid=?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, userDetails.getUserId());
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {

					customer.setAadhaar(resultSet.getLong("aadhaar"));
					customer.setAddress(resultSet.getString("address"));
					customer.setDateOfBirth(resultSet.getLong("dateofBirth"));
					customer.setEmail(resultSet.getString("email"));
					customer.setMobile(resultSet.getLong("mobile"));
					customer.setName(resultSet.getString("name"));
					customer.setPancard(resultSet.getString("pancard"));
					customer.setUserStatus(resultSet.getString("user_status"));
					customer.setUserId(resultSet.getInt("userid"));
				}
			}
		} catch (SQLException e) {
			throw new HelperException(e);
		}
		return customer;
	}

	@Override
	public AccountInfo getAccountDetails(AccountInfo accountInfo) throws HelperException {
		AccountInfo account = new AccountInfo();
		String query = "select * from AccountInfo where account_number=?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setBigDecimal(1, new BigDecimal(accountInfo.getAccountNumber()));
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					account.setAccountBalance(resultSet.getDouble("account_balance"));
					account.setAccountStatus(resultSet.getString("account_status"));
					account.setAccountNumber(resultSet.getLong("account_number"));
					account.setAccountType(resultSet.getString("account_status"));
					account.setIfscCode(resultSet.getString("ifsc_code"));
					account.setUserId(resultSet.getInt("userid"));
				}
			}
		} catch (SQLException e) {
			throw new HelperException(e);
		}
		return account;
	}

	private double getBalance(Long accountNumber) throws HelperException {
		double balance = 0;
		String query = "select account_balance from AccountInfo where account_number=?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, accountNumber);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					balance = resultSet.getDouble("account_balance");
				}
			}
		} catch (SQLException e) {
			throw new HelperException(e);
		}
		return balance;
	}

	@Override
	public double getTotalBalance(UserInfo user) throws HelperException {
		double balance = 0;
		String query = "select account_balance from AccountInfo where userid=?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, user.getUserId());
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					double temp = resultSet.getDouble("account_balance");
					balance = balance + temp;
				}
			}
		} catch (SQLException e) {
			throw new HelperException(e);
		}
		return balance;
	}

	@Override
	public Map<Long, AccountInfo> getAccountDetails(UserInfo user) throws HelperException {
		int userId = user.getUserId();
		Map<Long, AccountInfo> map = new HashMap<Long, AccountInfo>();
		String query = "select * from AccountInfo where userid=?";
		ResultSet resultSet = null;
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, userId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				AccountInfo account = new AccountInfo();
				userId = resultSet.getInt("userid");
				account.setUserId(userId);
				account.setAccountBalance(resultSet.getDouble("account_balance"));
				long accountNumber = resultSet.getLong("account_number");
				account.setAccountNumber(accountNumber);
				account.setAccountStatus(resultSet.getString("account_status"));
				account.setAccountType(resultSet.getString("account_type"));
				account.setBranchName(resultSet.getString("branch_name"));
				account.setIfscCode(resultSet.getString("ifsc_code"));
				map.put(accountNumber, account);
			}
			return map;
		} catch (SQLException e) {
			throw new HelperException(e);
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
			}
		}
	}

	@Override
	public Map<Integer, Map<Long, AccountInfo>> getAllAccountDetails() throws HelperException {
		Map<Integer, Map<Long, AccountInfo>> outerMap = new HashMap<Integer, Map<Long, AccountInfo>>();
		String query = "select * from AccountInfo order by userid";
		ResultSet resultSet = null;
		Map<Long, AccountInfo> innerMap = new HashMap<Long, AccountInfo>();
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				AccountInfo account = new AccountInfo();

				if (!outerMap.containsKey(resultSet.getInt("userid"))) {
					innerMap = new HashMap<Long, AccountInfo>();
				}

				account.setUserId(resultSet.getInt("userid"));
				account.setAccountBalance(resultSet.getDouble("account_balance"));
				account.setAccountNumber(resultSet.getLong("account_number"));
				account.setAccountStatus(resultSet.getString("account_status"));
				account.setAccountType(resultSet.getString("account_type"));
				account.setBranchName(resultSet.getString("branch_name"));
				account.setIfscCode(resultSet.getString("ifsc_code"));

				innerMap.put(resultSet.getLong("account_number"), account);
				outerMap.put(resultSet.getInt("userid"), innerMap);
			}
		} catch (SQLException e) {
			throw new HelperException(e);
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
			}
		}
		return outerMap;
	}

	@Override
	public Map<Integer, CustomerInfo> getAllUserDetails() throws HelperException {
		String query = "select * from CustomerInfo join UserInfo on UserInfo.userid=CustomerInfo.userid";
		ResultSet resultSet = null;
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			resultSet = statement.executeQuery();
			return storeDetailsInMapForCustomer(resultSet);
		} catch (SQLException e) {
			throw new HelperException(e);
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
			}
		}
	}

	@Override
	public List<TransactionInfo> getTransactionDetails(AccountInfo account) throws HelperException {
		Long accountNumber = account.getAccountNumber();
		String query = "select * from TransactionInfo where account_number=?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, accountNumber);
			try (ResultSet resultSet = statement.executeQuery()) {
				return setValuesInTransactionPojo(resultSet);
			}
		} catch (SQLException e) {
			throw new HelperException(e);
		}
	}

	@Override
	public List<TransactionInfo> getAllTransactionDetailsWithDate(int days) throws HelperException {
		Long date = HelperUtils.minusDates(days);
		String query = "select * from TransactionInfo where transaction_date > ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, date);
			ResultSet resultSet = statement.executeQuery();
			return setValuesInTransactionPojo(resultSet);
		} catch (SQLException e) {
			throw new HelperException(e);
		}
	}

	@Override
	public List<TransactionInfo> getSingleUserTransactionDetails(UserInfo user) throws HelperException {
		int userId = user.getUserId();
		String query = "select * from TransactionInfo where userid=?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, userId);
			try (ResultSet resultSet = statement.executeQuery()) {
				return setValuesInTransactionPojo(resultSet);
			}
		} catch (SQLException e) {
			throw new HelperException(e);
		}
	}

	@Override
	public boolean makeTransaction(TransactionInfo transaction) throws HelperException {
		long sender = transaction.getSenderAccountNumber(), receiver = transaction.getReceiverAccountNumber();
		double transactionAmount = transaction.getTransactionAmount();
		String type = transaction.getTransactionType();
		double rBalance = getBalance(receiver), sBalance = getBalance(sender);
		if (type.equalsIgnoreCase("withdraw")) {
			rBalance = rBalance - transactionAmount;
			transaction.setCurrentBalance(rBalance);
			insertIntoTransactionTable(transaction, 1);
			return true;
		} else if (type.equalsIgnoreCase("deposit")) {
			rBalance = rBalance + transactionAmount;
			transaction.setCurrentBalance(rBalance);
			insertIntoTransactionTable(transaction, 1);
			return true;
		} else {
			rBalance = rBalance + transactionAmount;
			transaction.setCurrentBalance(sBalance - transactionAmount);
			if (insertIntoTransactionTable(transaction, 1)) {
				int userid = getUserId(receiver);
				transaction.setUserId(userid);
				transaction.setAccountNumber(receiver);
				transaction.setCurrentBalance(rBalance);
				insertIntoTransactionTable(transaction, 0);
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean setUserStatus(CustomerInfo customer) throws HelperException {
		int userId = customer.getUserId();
		String status = customer.getUserStatus();
		String query = "update CustomerInfo set user_status=? where userid=?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, userId);
			statement.setString(2, status);
			if (statement.executeUpdate() == 1) {
				return true;
			}
		} catch (SQLException e) {
			throw new HelperException(e);
		}
		return false;
	}

	@Override
	public boolean setAccountStatus(AccountInfo account) throws HelperException {
		Long accountNumber = account.getAccountNumber();
		String state = account.getAccountStatus();
		String query = "update AccountInfo set account_states=? where account_number=?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, accountNumber);
			;
			statement.setString(2, state);
			if (statement.executeUpdate() == 1) {
				return true;
			}
		} catch (SQLException e) {
			throw new HelperException(e);
		}
		return false;
	}

	@Override
	public List<TransactionInfo> getAllTransactionDetails() throws HelperException {
		String query = "select * from TransactionInfo";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet resultSet = statement.executeQuery();
			return setValuesInTransactionPojo(resultSet);
		} catch (SQLException e) {
			throw new HelperException(e);
		}
	}

	

	@Override
	public boolean closeResource() throws HelperException {
		if(connection!=null) {
			try {
				connection.close();
			} catch (SQLException e) {
			}
			return true;
		}
		return false;
	}

	@Override
	public UserInfo getDetails(UserInfo user) throws HelperException {
		String query="select * from UserInfo where userid=?";
		try(PreparedStatement statement=connection.prepareStatement(query)){
			statement.setInt(1, user.getUserId());
			try(ResultSet resultSet=statement.executeQuery()){
				if(resultSet.next()) {
					user.setType(resultSet.getString("type"));
					user.setDateOfBirth(resultSet.getLong("dateofBirth"));
					user.setAddress(resultSet.getString("address"));
					user.setEmail(resultSet.getString("email"));
					user.setMobile(resultSet.getLong("mobile"));
					user.setName(resultSet.getString("name"));
				}
				return user;
			}
		} catch (SQLException e) {
			throw new HelperException(e); 
		}
	}
}
