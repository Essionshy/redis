package com.tingyu.redis.api;

import java.util.Set;

import com.tingyu.redis.util.JedisPoolUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 
 * @author Essionshy
 *
 */
public class JedisAPI {
	private static JedisPool jedisPool = JedisPoolUtils.getJedisPoolInstance();

	public static void main(String[] args) throws InterruptedException {
		// 测试连接
		Jedis jedis = jedisPool.getResource();
		System.out.println(jedis.ping());
		// db command
		jedis.select(1);// 切换数据库,返回值为String ok
		System.out.println("清空前数据库大小：" + jedis.dbSize());
		Thread.sleep(1 * 1000);// Main线程休息5秒钟后，清空数据库
		jedis.flushDB();
		System.out.println("清空后数据库大小：" + jedis.dbSize());

		System.out.println("jedis.exists(key):" + jedis.exists("id"));		
		// String
		jedis.getSet("id", "1001");
		jedis.getSet("name", "redis");
		Set<String> keys = jedis.keys("*");
		for (String key : keys) {
			System.out.println(key + "\t :" + jedis.get(key));
		}

	}
}
