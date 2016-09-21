package work.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import work.model.dto.Member;

/** ȸ�� ���̺� DAO Ŭ���� 
 * -- memebers_dml.sql �����ؼ� crud ��� �����ϼ���
 * 
 *  ## DAO Pattern (Data Access Object)
 *  -- Singleton Pattern ���� ����
 *   -- Factory Pattern ���� ����
 *
 *  ## Singleton Pattern
 *  -- �ϳ��� Ŭ������ ���ؼ� ���ϰ�ü(�ν��Ͻ�) ���� 
 *  -- �����Ģ :
 *     1. private ������(){}
 *     2. public static Ŭ���� getInstance() { return �ν��Ͻ�������; }
 *     3. private static Ŭ���� �ν��Ͻ������� = new Ŭ�����̸�(); 
 */
public class MemberDao {
   // FactoryDao Pattern ���� ���� : dao ���� ����� ���� FactoryDao ��ü ����
   private FactoryDao factory = FactoryDao.getInstance();
   
   // ## Singleton Pattern
   // 3. private static Ŭ���� �ν��Ͻ������� = new Ŭ�����̸�();
   private static MemberDao instance = new MemberDao();
   
   // 1. private ������(){}
   /** �⺻������ : jdbc driver �ε� ���� => FactoryDao ���� */
   private MemberDao() {}
   
   
   // 2. public static Ŭ���� getInstance() { return �ν��Ͻ�������; }
   public static MemberDao getInstance() {
      return instance;
   }

   /** ȸ�����
    * @param userId ���̵�
    * @param userPw ��й�ȣ
    * @param userName �̸�
    * @param coupleNo Ŀ�ù�ȣ
    * @return ��ȯ
    */
   public int join(Member user) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      
      try {
         conn = factory.getConnection();
         
         String sql = "insert into member values(?, ?, ?, ?)";      
         pstmt = conn.prepareStatement(sql);
         
         pstmt.setString(1, user.getUserId());
         pstmt.setString(2, user.getUserPw());
         pstmt.setString(3, user.getUserName());
         pstmt.setInt(4, user.getCoupleNo());
         
         System.out.println(user.getUserName() + "���� ȸ�������� �Ϸ�Ǿ����ϴ�.");
         return pstmt.executeUpdate();
         
      } catch(SQLException e) {
         System.out.println("error: ��� ����");
         e.printStackTrace();
      } finally {
         factory.close(conn, pstmt);
      }
     
      return 0;
   }
   
   /**
    * �α���
    * @param userId ���̵�
    * @param userPw ��й�ȣ
    * @return �α��� ����� �̸�(name), Ŀ�ù�ȣ(coupleNo) HashMap, 
    *           ����, ���̵���ų�, ��ȣ�� Ʋ���� null ��ȯ 
    */
   public HashMap<String, String> selectLogin(String userId, String userPw) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         conn = factory.getConnection();
         
         String sql = "select MEMBER_NAME, couple_no from member where id=? and pw=?";
         
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, userId);
         pstmt.setString(2, userPw);
         rs = pstmt.executeQuery();
         
         if(rs.next()) {
            HashMap<String, String> loginMap = new HashMap<String, String>();
            loginMap.put("userName", rs.getString(1));
            loginMap.put("coupleNo", rs.getString(2));

            
            System.out.println("\n## dao result : " + loginMap);
            return loginMap;
         }
         
      } catch(SQLException e) {
         System.out.println("error: �α��� ����");
         e.printStackTrace();
      } finally {
         factory.close(conn, pstmt, rs);
      }
      
      return null;
   }
   
   /**
    * Ư�� ȸ�� �� ��ȸ / ��������ȸ
    * @param userId ��ȸ ȸ�� ���̵�
    * @return ��ȸȸ����ü, ���̵� �������� �ʴ� ��� null ��ȯ
    */
   public Member selectOne(String userId) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      String userPw = null;
      String userName = null;
      int coupleNo = 0;
      
      try {
         conn = factory.getConnection();
         
         String sql = "select * from member where id = ?";      
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, userId);
         rs = pstmt.executeQuery();
         
         if(rs.next()) {
            userId = rs.getString("id");
            userPw = rs.getString("pw");
            userName = rs.getString("MEMBER_NAME");
            coupleNo = rs.getInt("couple_no");
            
            return new Member(userId, userPw, userName, coupleNo);
         }
         
      } catch(SQLException e) {
         System.out.println("error: ȸ�� ����ȸ ����");
         e.printStackTrace();
      } finally {
         factory.close(conn, pstmt, rs);
      }
      
      return null;
   }
   
   /**
    * ���̵� �ߺ� ��ȸ
    * @param userId ���̵�
    * @return ����� true, ������� false
    */
   public boolean isUserId(String userId) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         conn = factory.getConnection();
         
         String sql = "select MEMBER_NAME from member where id=?";      
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, userId);
         rs = pstmt.executeQuery();
         
         if(rs.next()) {
            return true;
         }
         
      } catch(SQLException e) {
         System.out.println("error: ���̵� �ߺ���ȸ ����");
         e.printStackTrace();
      } finally {
         factory.close(conn, pstmt, rs);
      }
      
      return false;
   }
}