package com.tingyu.redis.util;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * JedisPool工具类
 * 
 * @author Essionshy
 *
 */
public class JedisPoolUtils {
	private static final String HOST = "192.168.1.158";
	private static final int PORT = 6379;
	private volatile static JedisPool jedisPool = null;

	// 私有化无参构造，使工具类不能被实例化
	private JedisPoolUtils() {
	};

	public static JedisPool getJedisPoolInstance() {

		if (null == jedisPool) {

			synchronized (JedisPoolUtils.class) {
				if (null == jedisPool) {
					JedisPoolConfig poolConfig = new JedisPoolConfig();
					poolConfig.setMaxWaitMillis(100 * 1000);//单位ms
					poolConfig.setTestOnBorrow(true);
					poolConfig.setMaxIdle(32);
					jedisPool = new JedisPool(poolConfig, HOST, PORT);
				}
			}
		}

		return jedisPool;
	}

}
