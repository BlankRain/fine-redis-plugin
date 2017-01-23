package com.fr.felix.plugin.db.query.fun;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class SetDF implements DataFetcher {
	Jedis jedis;
	String key;
	String startTag = "#{";
	String endTag = "}";

	public SetDF(Jedis j, String key) {
		this.jedis = j;
		this.key = key;
	}

	public List<RedisKVData> data() {
		List<RedisKVData> data = new ArrayList<RedisKVData>();
		Set<String> val = jedis.smembers(key);
		List<String> vall = new ArrayList<String>(val);
		for (int i = 0; i < vall.size(); i++) {
			RedisKVData e = new RedisKVData();
			e.key = key;
			e.keyExpand = key + startTag + i + endTag;
			e.value = vall.get(i);
			data.add(e);
		}
		return data;
	}

}
