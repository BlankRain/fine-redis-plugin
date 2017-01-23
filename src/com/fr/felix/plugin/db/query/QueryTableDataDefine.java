package com.fr.felix.plugin.db.query;

import com.fr.base.TableData;
import com.fr.design.data.tabledata.tabledatapane.AbstractTableDataPane;
import com.fr.design.fun.ServerTableDataDefineProvider;
import com.fr.design.fun.impl.AbstractTableDataDefineProvider;
import com.fr.general.Inter;
import com.fr.felix.plugin.db.query.fun.QueryTableData;
import com.fr.felix.plugin.db.query.ui.QueryTableDataPane;

/**
 */
public class QueryTableDataDefine extends AbstractTableDataDefineProvider implements ServerTableDataDefineProvider {

    public Class<? extends TableData> classForTableData() {
        return QueryTableData.class;
    }

    public Class<? extends TableData> classForInitTableData() {
        return QueryTableData.class;
    }

    @SuppressWarnings("rawtypes")
	public Class<? extends AbstractTableDataPane> appearanceForTableData() {
        return QueryTableDataPane.class;
    }

    public String nameForTableData() {
        return Inter.getLocText("Plugin-Table_Data_Query_Name");
    }

    public String prefixForTableData() {
        return "redis";
    }

    public String iconPathForTableData() {
        return "/com/fr/felix/plugin/db/query/ui/images/query.png";
    }
}
