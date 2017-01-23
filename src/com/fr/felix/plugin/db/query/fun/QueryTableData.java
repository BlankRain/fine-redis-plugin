package com.fr.felix.plugin.db.query.fun;

import com.fr.base.ParameterHelper;
import com.fr.data.AbstractParameterTableData;
import com.fr.general.data.DataModel;
import com.fr.script.Calculator;
import com.fr.stable.ParameterProvider;
import com.fr.stable.StringUtils;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLableReader;

/**
 * Created by richie on 2016/10/8.
 */
public class QueryTableData extends AbstractParameterTableData {

	private static final String XML_ATTR_TAG = "QueryTableDataAttr";

	private String host;
	private String pwd;
	private String port;
	private String key;

	public QueryTableData() {
		processParameters();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public DataModel createDataModel(Calculator calculator) {

		return new QueryDataModel(processParameters(calculator), port, pwd, host, key);
	}

	public ParameterProvider[] getParameters(Calculator c) {
		processParameters();
		return super.getParameters(c);
	}

	private void processParameters() {
		if (this.parameters == null && StringUtils.isNotEmpty(host)) {
			this.parameters = ParameterHelper.analyze4Parameters(host, false);
		}
	}

	protected ParameterProvider[] processParameters(Calculator calculator) {
		processParameters();
		return Calculator.processParameters(calculator, this.parameters);
	}

	@Override
	public void readXML(XMLableReader reader) {
		super.readXML(reader);
		if (reader.isChildNode()) {
			String tagName = reader.getTagName();
			if (tagName.equals(XML_ATTR_TAG)) {
				host = reader.getAttrAsString("host", StringUtils.EMPTY);
				key = reader.getAttrAsString("key", StringUtils.EMPTY);
				port = reader.getAttrAsString("port", StringUtils.EMPTY);
				pwd = reader.getAttrAsString("pwd", StringUtils.EMPTY);
			}
		}
	}

	@Override
	public void writeXML(XMLPrintWriter writer) {
		super.writeXML(writer);
		writer.startTAG(XML_ATTR_TAG);
		writer.attr("host", host);
		writer.attr("key", key);
		writer.attr("port", port);
		writer.attr("pwd", pwd);
		writer.end();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		QueryTableData cloned = (QueryTableData) super.clone();
		cloned.host = host;
		cloned.port = port;
		cloned.pwd = pwd;
		cloned.key = key;
		return cloned;
	}
}
