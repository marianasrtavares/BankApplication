package pt.mrdb.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {

	private static final String url = "jdbc:mysql://localhost:3306/mrdb_new";
	private static final String user = "root";
	private static final String password = "P@ssw0rd";
	private static Connection conn;
	private static Statement st;

	public static void init() {
		try {
			conn = DriverManager.getConnection(url, user, password);
			st = conn.createStatement();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ResultSet executeQuery(String query, Operation operation) {

		if (conn == null) {
			init();
		}
		try {
			switch (operation) {
			case SELECT:
				st.executeQuery(query);
				return st.getResultSet();
			case UPDATE:
				st.executeUpdate(query);
				return st.getResultSet();
			case INSERT:
				st.executeUpdate(query);
				return st.getResultSet();
			case DELETE:
				st.executeUpdate(query);
				return st.getResultSet();

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("Cannot execute the selected query");
	}
}
