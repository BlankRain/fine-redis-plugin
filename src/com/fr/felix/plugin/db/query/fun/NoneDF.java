package com.fr.felix.plugin.db.query.fun;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fr.base.FRContext;

import redis.clients.jedis.Jedis;

/**
 * 未能匹配到的.
 *
 */
public class NoneDF implements DataFetcher {
	Jedis jedis;
	String key;

	public NoneDF(Jedis j, String key) {
		this.jedis = j;
		this.key = key;
	}

	public List<RedisKVData> data() {
		List<RedisKVData> data = new ArrayList<RedisKVData>();
		boolean isPattern=false;
		/*
		 * see @ https://redis.io/commands/keys for support pattern
		 * */
		String[] signs=new String[]{"[","]","*","?"};
		for(String sign:signs){
			if(key.contains(sign)){
				isPattern=true;
				break;
			}
		}
		
		if(isPattern){
			Set<String> ks = jedis.keys(key);
			/**
			 * 依次把每个Key的数据拿出来
			 */
			for (String k : ks) {
				List<RedisKVData> kdata = Tool.getDataByKey(jedis, k);
				// 整个世界安静了...
				data.addAll(kdata);
			}
			/**
			 * 对于统配模式, 整理一下 Key 及 KeyExpand
			 */
			for (RedisKVData kv : data) {
				kv.key = key + "=>" + kv.key;
				kv.keyExpand = key + "=>" + kv.keyExpand;
			}
		}
		return data;
	}

}
