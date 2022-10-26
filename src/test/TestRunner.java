package test;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

//
//import java.math.BigInteger;
//import java.sql.SQLException;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import help.HelperException;
//import pojo.AccountInfo;
//import pojo.CustomerInfo;
//import pojo.TransactionInfo;
//import pojo.UserInfo;
//import storage.*;
//import util.HelperUtils;
//
//@SuppressWarnings("unused")
public class TestRunner {
	static Logger logger=Logger.getLogger("GLOBAL_LOGGER_NAME");
////	@SuppressWarnings("unlikely-arg-type")
	public static void main(String[] args)  {
//		StorageLayer sl=new DataProcess();
//		String role=null;
//		double balance=0;
//		try {
//			UserInfo user=new UserInfo();
//			user.setUserId(1);
//			user.setPassword("SIA@314");
//			user=sl.validateUser(user);
//			role=user.getType();
////			CustomerInfo cus=sl.getStatus(6);
////			role=cus.getUserStatus();
////			AccountInfo acc=sl.getAccountStatus(new BigInteger("2200001"));
////			role=acc.getAccountStatus();
////			AccountInfo acc=sl.getBalance(new BigInteger("2200000"));
////			balance=acc.getAccountBalance();
////			balance=sl.getTotalBalance(4);
////			Map<Long, AccountInfo> map=sl.getAccountDetails(5);
////				Collection<AccountInfo> t2=map.values();
////				for(AccountInfo a:t2) {
////					System.out.println(a.getAccountBalance()+" "+a.getAccountType()+" "+a.getUserId());
////				}
//
////			Map<Long, AccountInfo> map=sl.getAccountDetails(2);
////				Collection<AccountInfo> t2=map.values();
////				for(AccountInfo a:t2) {
////					System.out.println(a.getAccountBalance()+" "+a.getAccountNumber()+" "+a.getAccountType()+" "+a.getUserId());
////				}
//			Map<Integer,CustomerInfo> map=sl.getAllUserDetails();
//				Collection<CustomerInfo> t2=map.values();
//				for(CustomerInfo t4:t2) {
//					System.out.println(t4.getUserId()+" "+t4.getType()+" "+t4.getUserStatus());
//				}
//				Collection<Integer> t5=map.keySet();
//				for(Integer f:t5 ) {
//					System.out.println(f);
//				}
////			Map<Integer,Map<Long,AccountInfo>> map=sl.getAllAccountDetails();
////			Collection<Map<Long,AccountInfo>> t=map.values();
////			for(Map<Long,AccountInfo> temp:t) {
////				Collection<AccountInfo> f=temp.values();
////				System.out.println("userid\t Account number\tAccount Type\tAccount Balance");
////				for(AccountInfo a:f) {
////					System.out.println();
////				System.out.println(a.getUserId()+"\t"+a.getAccountNumber()+"\t"+a.getAccountType()+"\t"+a.getAccountBalance());
////				}
////			}
////			TransactionInfo t=new TransactionInfo();
////			t.setAccountNumber(2200001l);
////			t.setTransactionDate(System.currentTimeMillis());
////			t.setUserId(4);
////			t.setSenderAccountNumber(2200005l);
////			t.setReceiverAccountNumber(2200001l);
////			double amount=5;
////			t.setTransactionAmount(amount);
////			t.setTransactionType("transfer");
////			System.out.println(sl.makeTransaction(t));
////			List<TransactionInfo> tr=sl.getAllTransactionDetails();
////			System.out.println("Transactionid\tUserId\tAccountNumber\t\tDate\tSender\tReceiver\tamount\tclosingBalance\tType");
////			for(TransactionInfo f:tr) {
////				System.out.println(f.getTransactionId()+"\t\t"+f.getUserId()+"\t"+f.getAccountNumber()+"\t\t"+f.getTransactionDate()+"\t"+f.getSenderAccountNumber()+"\t "+f.getReceiverAccountNumber()+"\t"+f.getTransactionAmount()+"\t"+f.getCurrentBalance()+"\t\t"+f.getTransactionType());
////			}
////			String date=HelperUtils.getDateTime(System.currentTimeMillis());
////			System.out.println(date);
////			long mili=HelperUtils.minusDates(1);
////			System.out.println(System.currentTimeMillis());
////			System.out.println(mili+" "+HelperUtils.getDateTime(mili));
//			
//		} catch (HelperException e) {
//			e.printStackTrace();
//		}
////		System.out.println(balance);
//		System.out.println(role);
//	}
	
	
	long second=System.currentTimeMillis();
//	int number=5;
//	String s= ""+number+second;
//	System.out.println(s);
//	System.out.println(second%1666670000000l);
		Random r=new Random();
		String a="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		char le=a.charAt(r.nextInt(a.length()));
		String re=String.valueOf(le).concat(String.valueOf(second%1666670000000l));
		System.out.println(re);
		logger.log(Level.INFO,re);
	}
}
