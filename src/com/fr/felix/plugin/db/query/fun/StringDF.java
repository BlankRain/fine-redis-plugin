package com.fr.felix.plugin.db.query.fun;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;

public class StringDF implements DataFetcher {
	Jedis jedis;
	String key;

	public StringDF(Jedis j, String key) {
		this.jedis = j;
		this.key = key;
	}

	public List<RedisKVData> data() {
		List<RedisKVData> data = new ArrayList<RedisKVData>();
		RedisKVData e = new RedisKVData();
		e.key = key;
		e.keyExpand = key;
		e.value = jedis.get(key);
		data.add(e);
		return data;
	}

}
