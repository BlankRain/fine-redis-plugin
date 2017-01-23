package com.fr.felix.plugin.db.query.fun;

import java.util.List;

import com.fr.base.FRContext;
import com.fr.general.data.DataModel;
import com.fr.general.data.TableDataException;
import com.fr.stable.ParameterProvider;

import redis.clients.jedis.Jedis;

/**
 */
public class QueryDataModel implements DataModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ParameterProvider[] parameters;
	private String host;
	private String port;
	private String pwd;
	private String key;
	public int defaultTimeout = 3000;
	public int defaultPort = 6379;
	List<RedisKVData> data = null;
	Jedis j = null;

	public QueryDataModel(ParameterProvider[] parameters, String port, String pwd, String host, String key) {
		this.parameters = parameters;
		this.host = host;
		this.pwd = pwd;
		this.port = port;
		this.key = key;

	}

	public void fetchData() {
		String jhost = handleHost(host);
		int jport = handlePort(port);
		int timeout = handleTimeOut();
		j = new Jedis(jhost, jport, timeout);
		if (pwd != null && !pwd.isEmpty()) {
			j.auth(pwd);
		}
		String k = j.ping();
		FRContext.getLogger().info("Ping With Response:" + k);
		if (String.valueOf(k).toLowerCase().equals("pong")) {
			data=Tool.getDataByKey(j,key);
		}
	}

	

	/**
	 * 解析域名及端口
	 *
	 */
	private String handleHost(String host) {
		return host;
	}

	/**
	 * 解析超時
	 */
	private int handleTimeOut() {
		return defaultTimeout;
	}

	/**
	 * 解析端口
	 *
	 */
	private int handlePort(String host2) {

		return defaultPort;
	}

	public int getColumnCount() throws TableDataException {
		return 3;
	}

	public String getColumnName(int i) throws TableDataException {
		String[] cn = new String[] { "key", "keyExpand", "value" };
		return cn[i];
	}

	public boolean hasRow(int i) throws TableDataException {
		if (data == null) {
			fetchData();
		}
		return i < data.size();
	}

	public int getRowCount() throws TableDataException {
		if (data == null) {
			fetchData();
		}
		return data.size();
	}

	public Object getValueAt(int x, int y) throws TableDataException {
		if (data == null) {
			fetchData();
		}
		return data.get(x).get(y);
	}

	public void release() throws Exception {
		if (j != null) {
			j.disconnect();
		}
		j = null;
		data = null;
		System.gc();
	}
}

/**
 * Redis 返回数据, 处理后结果
 * 
 * @author Administrator
 *
 */
class RedisKVData {
	/**
	 * 查询Key
	 */
	String key;
	/**
	 * Redis 有内置数据结构,按结构展开后的Key
	 */
	String keyExpand;
	/**
	 * 值,String.valueOf后结果
	 */
	String value;

	String get(int i) {
		return new String[] { key, keyExpand, value }[i];
	}
}