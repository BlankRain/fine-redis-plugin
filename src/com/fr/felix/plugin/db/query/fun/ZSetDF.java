package com.fr.felix.plugin.db.query.fun;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class ZSetDF implements DataFetcher {
	Jedis jedis;
	String key;
	String startTag = "#{";
	String endTag = "}";

	public ZSetDF(Jedis j, String key) {
		this.jedis = j;
		this.key = key;
	}

	public List<RedisKVData> data() {
		List<RedisKVData> data = new ArrayList<RedisKVData>();
		long start = 0;
		long end = jedis.zcard(key);
		
		Set<String> val = jedis.zrange(key, start, end);
		List<String> vall = new ArrayList<String>(val);
		for (int i = 0; i < vall.size(); i++) {
			RedisKVData e = new RedisKVData();
			e.key = key;
			e.value = vall.get(i);
			double score=jedis.zscore(key, e.value);
			e.keyExpand = key + startTag + score +"-"+i +endTag;
			data.add(e);
		}
		return data;
	}

}
