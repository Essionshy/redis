package com.tingyu.redis.transaction;
/**
 * Redis事务测试类
 * 
 * @author Essionshy
 *
 */
public class TransactionDemo {
	public static void main(String[] args) throws InterruptedException {
		CreditCard creditCard = new CreditCard();
		creditCard.init();			
		
		creditCard.setConsumAmount(10000);	
		String result = (creditCard.consumer()) ? "success" : "fail";
		System.out.println("交易状态：" + result);
		
	}
}
