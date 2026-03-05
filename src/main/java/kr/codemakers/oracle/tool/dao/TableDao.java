package kr.codemakers.oracle.tool.dao;

import java.util.ArrayList;
import java.util.List;

import kr.codemakers.oracle.tool.db.Querys;
import kr.codemakers.oracle.tool.db.helper.DataAccessHelper;
import kr.codemakers.oracle.tool.db.helper.SQLType;
import kr.codemakers.oracle.tool.entity.vo.Column;
import kr.codemakers.oracle.tool.entity.vo.Table;

public class TableDao {

	private DataAccessHelper helper;
	
	public TableDao(String url, int port, String database, String username, String password) {
		this.helper = new DataAccessHelper(url, port, database, username, password);
	}

	public void reconnect(String url, int port, String database, String username, String password) {
		this.helper = new DataAccessHelper(url, port, database, username, password);
	}
	
	public void close() {
		this.helper.close();
	}
	
	public List<Table> getAllTables(String username) {
		
		
		List<Table> tableList = new ArrayList<>();
		this.helper.preparedStatement(Querys.ALL_TABS, pstmt -> {
			pstmt.setString(1, username);
		});
		this.helper.executeQuery(SQLType.SELECT, row -> {
			String tableName = row.getString("TABLE_NAME");
			String comments = row.getString("COMMENTS");
			tableList.add(new Table(tableName, comments));
		});
		
		return tableList;
	}
	
	public Table getOneTable(String username, String tableName) {
		Table table = new Table();
		this.helper.preparedStatement(Querys.ONE_TAB, pstmt -> {
			pstmt.setString(1, username);
			pstmt.setString(2, tableName);
		});
		this.helper.executeQuery(SQLType.SELECT, row -> {
			table.setTableName(row.getString("TABLE_NAME"));
			table.setComments(row.getString("COMMENTS"));
		});
		
		return table;
	}
	
	public List<Column> getAllColumns(String username, String tableName) {
		
		List<Column> columnList = new ArrayList<>();
		this.helper.preparedStatement(Querys.ALL_COLS, pstmt -> {
			pstmt.setString(1, username);
			pstmt.setString(2, tableName);
		});
		this.helper.executeQuery(SQLType.SELECT, row -> {
			String columnName = row.getString("COLUMN_NAME");
			String dataType = row.getString("DATA_TYPE");
			String comments = row.getString("COMMENTS");
			String length = row.getString("LENGTH");
			columnList.add(new Column(columnName, dataType, comments, length));
		});
		
		return columnList;
	}
}
