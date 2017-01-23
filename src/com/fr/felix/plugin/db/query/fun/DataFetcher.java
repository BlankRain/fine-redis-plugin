package com.fr.felix.plugin.db.query.fun;

import java.util.List;
/**
 * 我就是个抓数据的..
 *
 */
public interface DataFetcher {
	public List<RedisKVData> data();
}
