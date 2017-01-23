package com.fr.felix.plugin.db.query.ui;

import com.fr.base.BaseUtils;
import com.fr.design.actions.UpdateAction;
import com.fr.design.data.datapane.preview.PreviewTablePane;
import com.fr.design.data.tabledata.tabledatapane.AbstractTableDataPane;
import com.fr.design.gui.ilable.UILabel;
import com.fr.design.gui.itextfield.UITextField;
import com.fr.design.gui.itoolbar.UIToolbar;
import com.fr.design.layout.TableLayout;
import com.fr.design.layout.TableLayoutHelper;
import com.fr.design.menu.ToolBarDef;
import com.fr.general.Inter;
import com.fr.felix.plugin.db.query.fun.QueryTableData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by richie on 2016/10/8.
 */
public class QueryTableDataPane extends AbstractTableDataPane<QueryTableData> {

	private static final String PREVIEW_BUTTON = Inter.getLocText("Preview");

	private UITextField hostTextField;
	private UITextField userTextField;
	private UITextField pwdTextField;
	private UITextField keyTextField;

	public QueryTableDataPane() {
		setLayout(new BorderLayout());
		add(createToolBar(), BorderLayout.NORTH);
		JPanel contentPane = new JPanel();
		add(contentPane, BorderLayout.CENTER);
		addChildrenToParent(contentPane);
		JTextArea jt = new JTextArea(Inter.getLocText("Redis-Plugin-Help"));
		jt.setEditable(false);
		add(jt, BorderLayout.SOUTH);
	}

	private JToolBar createToolBar() {
		ToolBarDef toolBarDef = new ToolBarDef();
		toolBarDef.addShortCut(new PreviewQueryTableDataAction());
		UIToolbar editToolBar = ToolBarDef.createJToolBar();
		toolBarDef.updateToolBar(editToolBar);
		return editToolBar;
	}

	private void addChildrenToParent(JPanel contentPane) {
		double p = TableLayout.PREFERRED;
		double f = TableLayout.FILL;
		double[] rowSize = { p };
		double[] columnSize = { p, f };
		int tl = 25;
		JPanel panel = TableLayoutHelper.createTableLayoutPane(
				new Component[][] { { new UILabel("主机:"), hostTextField = new UITextField(tl) } }, rowSize, columnSize);
		JPanel panel1 = TableLayoutHelper.createTableLayoutPane(
				new Component[][] { { new UILabel("端口:"), userTextField = new UITextField(tl) } }, rowSize, columnSize);
		JPanel panel2 = TableLayoutHelper.createTableLayoutPane(
				new Component[][] { { new UILabel("密码:"), pwdTextField = new UITextField(tl) } }, rowSize, columnSize);

		JPanel panel3 = TableLayoutHelper.createTableLayoutPane(
				new Component[][] { { new UILabel("Key:"), keyTextField = new UITextField(tl) } }, rowSize, columnSize);

		contentPane.add(panel);
		contentPane.add(panel1);
		contentPane.add(panel2);
		contentPane.add(panel3);
	}

	@Override
	public void populateBean(QueryTableData ob) {
		if (ob != null) {
			hostTextField.setText(ob.getHost());
			userTextField.setText(ob.getPort());
			pwdTextField.setText(ob.getPwd());
			keyTextField.setText(ob.getKey());
		}
	}

	@Override
	public QueryTableData updateBean() {
		QueryTableData tableData = new QueryTableData();
		tableData.setHost(hostTextField.getText());
		tableData.setPort(userTextField.getText());
		tableData.setPwd(pwdTextField.getText());
		tableData.setKey(keyTextField.getText());
		return tableData;
	}

	@Override
	protected String title4PopupWindow() {
		return Inter.getLocText("Plugin-Table_Data_Query_Name");
	}

	private class PreviewQueryTableDataAction extends UpdateAction {
		public PreviewQueryTableDataAction() {
			this.setName(PREVIEW_BUTTON);
			this.setMnemonic('P');
			this.setSmallIcon(BaseUtils.readIcon("/com/fr/design/images/m_file/preview.png"));
		}

		public void actionPerformed(ActionEvent evt) {
			PreviewTablePane.previewTableData(QueryTableDataPane.this.updateBean());
		}
	}

	public static void main(String[] args) {
		JFrame jf = new JFrame("test");
		jf.setSize(900, 600);
		QueryTableDataPane qtd = new QueryTableDataPane();
		jf.add(qtd);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
