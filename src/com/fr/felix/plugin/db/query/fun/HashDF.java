package com.fr.felix.plugin.db.query.fun;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class HashDF implements DataFetcher {
	Jedis jedis;
	String key;
	String startTag = "#";
	String endTag = ":";

	public HashDF(Jedis j, String key) {
		this.jedis = j;
		this.key = key;
	}

	public List<RedisKVData> data() {
		List<RedisKVData> data = new ArrayList<RedisKVData>();
		Map<String, String> val = jedis.hgetAll(key);
		Set<String> ks = val.keySet();
		for (String k : ks) {
			RedisKVData e = new RedisKVData();
			e.key = key;
			e.keyExpand = key + startTag + k + endTag;
			e.value = val.get(k);
			data.add(e);
		}
		return data;
	}

}
