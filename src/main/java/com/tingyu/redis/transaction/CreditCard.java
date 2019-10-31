package com.tingyu.redis.transaction;

import java.util.Date;

import com.tingyu.redis.util.JedisPoolUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

/**
 * 
 * @author Essionshy
 *
 */
public class CreditCard {
	private JedisPool jedisPool = JedisPoolUtils.getJedisPoolInstance();

	private Jedis jedis = jedisPool.getResource();
	private static long INITIAL_AMOUNT = 1000000L;
	private long cardId;//卡号
	private long balance;// 余额，单位分
	private long consumAmount;// 消费金额
	private long billAmount;// 账单金额

	/**
	 * 初始信用卡信息
	 */
	public void init() {
		this.setCardId(6127002510241456L);
		this.setBalance(INITIAL_AMOUNT);
		this.setBillAmount(0);
		System.out.println(jedis);
	}

	/**
	 * 模拟信用卡消费
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public boolean consumer() throws InterruptedException {
		balance = Long.parseLong(jedis.get("balance"));// 从Redis数据中读取账户余额
		jedis.watch("balance");
		// Thread.sleep(20*1000);//主线程睡10秒，在这期间模拟外部修改，测试成功

		if (consumAmount > balance) {
			jedis.unwatch();
			System.out.println("对不起，您的账户余额不足，无法消费，请重新更换卡片完成交易");
			return false;
		} else {
			Transaction transaction = jedis.multi();
			transaction.decrBy("balance", consumAmount);
			transaction.incrBy("billAmount", consumAmount);
			transaction.exec();
			printTransferInfo();
		}
		return true;
	}

	/**
	 * 打印交易日志信息
	 * 
	 * @throws InterruptedException
	 */
	public void printTransferInfo() throws InterruptedException {
		System.out.println("***************交易信息****************");
		System.out.println("消费金额：" + this.getConsumAmount() / 100);
		System.out.println("账户余额：" + this.getBalance() / 100);
		System.out.println("账单金额：" + this.getBillAmount() / 100);
		System.out.println("交易账户："+this.cardId);
		System.out.println("交易时间："+new Date());

	}

	// setter getter
	public long getBalance() {
		this.balance = Long.parseLong(jedis.get("balance"));
		return balance;
	}

	public void setBalance(long balance) {
		jedis.getSet("balance", String.valueOf(balance));
	}

	public long getConsumAmount() {
		return consumAmount;
	}

	public void setConsumAmount(long consumAmount) {
		this.consumAmount = consumAmount;
	}

	public long getBillAmount() {
		this.billAmount = Long.parseLong(jedis.get("billAmount"));
		return billAmount;
	}

	public void setBillAmount(long billAmount) {
		jedis.getSet("billAmount", String.valueOf(billAmount));

	}

	public long getCardId() {
		return cardId;
	}

	public void setCardId(long cardId) {
		this.cardId = cardId;
	}
	

}
