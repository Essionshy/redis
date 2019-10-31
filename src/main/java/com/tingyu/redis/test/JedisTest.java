package com.tingyu.redis.test;

import redis.clients.jedis.Jedis;

/**
 * 
 * @author Essionshy
 *
 */
public class JedisTest {
	private static final String HOST = "192.168.1.158";
	private static final int PORT = 6379;
	private static Jedis jedis = new Jedis(HOST, PORT);

	public static void main(String[] args) {
		// 测试连通
		String result = jedis.ping();
		System.out.println(result);
	}
}
