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
			System.out.println("DataSource 이름검색오류발생");
			e.getStackTrace();
		}
	}

	public static FactoryDao getInstance() {
		return instance;
	}
	//공통기능 : connection 생성반환 
	public Connection getConnection() {
		try{
		// connection pool (dataSource) 에게 연결객체 하나 가져와서 반환
		return ds.getConnection();
		}catch(SQLException e){
			System.out.println("DataSource 연결객체 가져오기 오류발생");
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
				stmt.close();// stmt부터 널이아니면 닫아줌
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error : 자원해제 오류");
			e.printStackTrace();
		}
	}

	public void close(Connection conn, Statement stmt) {
		close(conn, stmt, null);

	}
}
