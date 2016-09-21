/**
 * 
 */
package work.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * FactoryDao
 * @author ���ر�
 *
 */
public class FactoryDao {

   
   private String dsName = "java:comp/env/jdbc/Oracle";
   
   private DataSource ds;
   
   private static FactoryDao instance = new FactoryDao();

   private FactoryDao() {
      try {
         ds = (DataSource) new InitialContext().lookup(dsName);
      } catch (NamingException e) {
         System.out.println("DataSource �̸��˻������߻�");
         e.getStackTrace();
      }
   }

   /**
    * @return instance
    */
   public static FactoryDao getInstance() {
      return instance;
   }

   /**
    * @return null
    */
   public Connection getConnection() {
      try {
         return ds.getConnection();
      } catch (SQLException e) {
         System.out.println("DataSource ���ᰴü �������� �����߻�");
         e.getStackTrace();
      }
      return null;
   }

   /**
    * @param conn Connection
    * @param stmt Statement
    * @param rs ResultSet
    */
   public void close(Connection conn, Statement stmt, ResultSet rs) {
      try {
         if (rs != null) {
            rs.close();
         }
         if (stmt != null) {
            stmt.close();
         }
         if (conn != null) {
            conn.close();
         }
      } catch (SQLException e) {
         System.out.println("error : �ڿ����� ����");
         e.printStackTrace();
      }
   }

   /**
    * @param conn Connection
    * @param stmt Statement
    */
   public void close(Connection conn, Statement stmt) {
      close(conn, stmt, null);
   }
}