package com.fr.felix.plugin.db.query.fun;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;

public class Tool {
	public static List<RedisKVData> getDataByKey(Jedis j, String key) {
		List<RedisKVData> data = new ArrayList<RedisKVData>();
		String keyType = j.type(key);
		/**
		 * keytypes 和 dh 一一映射啊...
		 **/
		String[] keyTypes = new String[] { //
				"none", "string", "set", //
				"zset", "list", "hash" };//
		DataFetcher[] dh = new DataFetcher[] { //
				new NoneDF(j, key), new StringDF(j, key), new SetDF(j, key), //
				new ZSetDF(j, key), new ListDF(j, key), new HashDF(j, key) };//

		for (int i = 0; i < keyTypes.length; i++) {
			if (keyType.equals(keyTypes[i])) {
				data = dh[i].data();
				break;
			}
		}
		return data;
	}
}
