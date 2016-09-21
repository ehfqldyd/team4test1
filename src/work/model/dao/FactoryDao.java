package work.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class FactoryDao {
	//connection pool: javax.sql.DataSource
	
	//Resource name = jdbc/oracle ":context.html
	private String dsName="java:comp/env/jdbc/Oracle";
	private DataSource ds;
	private static FactoryDao instance = new FactoryDao();

	private FactoryDao() {
		try{
		ds= (DataSource)new InitialContext().lookup(dsName);
		} catch(NamingException e){
			System.out.println("DataSource �̸��˻������߻�");
			e.getStackTrace();
		}
	}

	public static FactoryDao getInstance() {
		return instance;
	}
	//������ : connection ������ȯ 
	public Connection getConnection() {
		try{
		// connection pool (dataSource) ���� ���ᰴü �ϳ� �����ͼ� ��ȯ
		return ds.getConnection();
		}catch(SQLException e){
			System.out.println("DataSource ���ᰴü �������� �����߻�");
			e.getStackTrace();
		}
		return null;
	}

	public void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();// stmt���� ���̾ƴϸ� �ݾ���
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error : �ڿ����� ����");
			e.printStackTrace();
		}
	}

	public void close(Connection conn, Statement stmt) {
		close(conn, stmt, null);

	}
}
