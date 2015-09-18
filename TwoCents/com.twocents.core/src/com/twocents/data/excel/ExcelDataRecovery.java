package com.twocents.data.excel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ExcelDataRecovery {

	/**
	 * Irá recuperar todas as transações do client, criando os demais dados envolvidos(corretos..)
	 */
	public static void readOperationFromExcel(String[] args) throws SQLException {
		Connection connection = null;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con = DriverManager
					.getConnection("jdbc:odbc:employee_xls");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("Select * from [Sheet1$]");

			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();

			System.out.println("No of cols " + numberOfColumns);

			while (rs.next()) {
				for (int i = 1; i <= numberOfColumns; i++) {
					if (i > 1)
						System.out.print(", ");
					String columnValue = rs.getString(i);
					System.out.print(columnValue);
				}
				System.out.println("");
			}
			rs.close();
			st.close();
		} catch (Exception ex) {
			System.err.print("Exception: ");
			System.err.println(ex.getMessage());
		} finally {
			connection.close();
		}
	}

}
