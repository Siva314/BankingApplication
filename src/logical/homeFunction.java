package logical;

import java.util.Map;
import java.util.Scanner;
//import java.util.logging.Logger;

import help.HelperException;
import pojo.AccountInfo;
import pojo.UserInfo;
import storage.DataProcess;
import storage.StorageLayer;

public class homeFunction {

	static homeFunction home=new homeFunction();
	static UserInfo user = new UserInfo();
	static StorageLayer storage = new DataProcess();
//	Logger logger=Logger.getLogger("GLOBAL_LOGGER_NAME");

	public static String checkLogin(int id, String password) throws HelperException {
		user.setUserId(id);
		user.setPassword(password);
		UserInfo temp = storage.validateUser(user);
		return temp.getType();
	}
	
	public void displayProfile(UserInfo user) {
		System.out.println("User id\tName\tDate of birth\tMobile\temail\ttype\taddress\n"+user.getUserId()
		+"\t"+user.getName()+"\t"+user.getDateOfBirth()+"\t"+user.getMobile()+"\t"+user.getEmail()+"\t"+user.getType()+"\t"+user.getAddress());
	}
	
	public void displayAccount(AccountInfo account) {
//		System.out.println("");
	}

	public static void main(String[] args) {
		String type;
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		Map<Long, AccountInfo> map;

		try {
			if (storage.openResource()) {
				type = checkLogin(2, "user@123");
				if (type != null) {

					if (type.equalsIgnoreCase("customer")) {
						System.out.println("Welcome Customer");
						UserFunctionality customer = new UserFunctionality(user);
						user=customer.getUserProfile();
						home.displayProfile(user);
						map = customer.getAccounts();
						System.out.println(map.keySet().size()+" "+map.keySet() + " " + customer.getTotalAccountBalance(user.getUserId()));
						Long accountNumber = sc.nextLong();
						AccountInfo account = new AccountInfo();
						account = customer.getAccountDetails(accountNumber);
						System.out.println(account.getUserId() + " " + account.getAccountNumber() + " "
								+ account.getAccountType()+" "+account.getAccountBalance());
						System.out.println("1.withdraw\t2.deposit\t3.transfer");
						int choice = sc.nextInt();
						if (choice == 1) {
							System.out.println("enter the amount");
							double amount = sc.nextDouble();
							if (customer.withdraw(amount)) {
								System.out.println("success");
							} else {
								System.out.println("failed");
							}
						} else if (choice == 2) {
							System.out.println("enter the amount");
							double amount = sc.nextDouble();
							if (customer.deposit(amount)) {
								System.out.println("success");
							} else {
								System.out.println("failed");
							}
						} else if (choice == 3) {
							System.out.println("enter the account number");
							Long number = sc.nextLong();
							System.out.println("enter the amount");
							double amount = sc.nextDouble();
							if (customer.transfer(number, amount)) {
								System.out.println("success");
							} else {
								System.out.println("failed");
							}
						}
					} else if (type.equalsIgnoreCase("admin")) {

					}
				} else {
					System.out.println("please check the userid and password");
				}
			} else {
				System.out.println("connection failed");
			}
		} catch (HelperException e) {
			e.printStackTrace();
		}

	}
}
