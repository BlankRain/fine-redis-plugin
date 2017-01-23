package com.fr.felix.plugin.db.query.fun;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;

public class ListDF implements DataFetcher {
	Jedis jedis;
	String key;
	String startTag = "[";
	String endTag = "]";

	public ListDF(Jedis j, String key) {
		this.jedis = j;
		this.key = key;
	}

	public List<RedisKVData> data() {
		List<RedisKVData> data = new ArrayList<RedisKVData>();
		long end = jedis.llen(key);
		long start = 0;
		List<String> val = jedis.lrange(key, start, end);
		for (int i = 0; i < val.size(); i++) {
			RedisKVData e = new RedisKVData();
			e.key = key;
			e.keyExpand = key + startTag + i + endTag;
			e.value = val.get(i);
			data.add(e);
		}
		return data;
	}

}
