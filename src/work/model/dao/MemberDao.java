package work.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import work.model.dto.Member;

/** 회원 테이블 DAO 클래스 
 * -- memebers_dml.sql 참고해서 crud 기능 구현하세요
 * 
 *  ## DAO Pattern (Data Access Object)
 *  -- Singleton Pattern 적용 설계
 *   -- Factory Pattern 적용 설계
 *
 *  ## Singleton Pattern
 *  -- 하나의 클래스에 대해서 단일객체(인스턴스) 서비스 
 *  -- 설계규칙 :
 *     1. private 생성자(){}
 *     2. public static 클래스 getInstance() { return 인스턴스변수명; }
 *     3. private static 클래스 인스턴스변수명 = new 클래스이름(); 
 */
public class MemberDao {
   // FactoryDao Pattern 적용 설계 : dao 공통 기능을 갖는 FactoryDao 객체 생성
   private FactoryDao factory = FactoryDao.getInstance();
   
   // ## Singleton Pattern
   // 3. private static 클래스 인스턴스변수명 = new 클래스이름();
   private static MemberDao instance = new MemberDao();
   
   // 1. private 생성자(){}
   /** 기본생성자 : jdbc driver 로딩 로직 => FactoryDao 위임 */
   private MemberDao() {}
   
   
   // 2. public static 클래스 getInstance() { return 인스턴스변수명; }
   public static MemberDao getInstance() {
      return instance;
   }

   /** 회원등록
    * @param userId 아이디
    * @param userPw 비밀번호
    * @param userName 이름
    * @param coupleNo 커플번호
    * @return 반환
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
         
         System.out.println(user.getUserName() + "님이 회원가입이 완료되었습니다.");
         return pstmt.executeUpdate();
         
      } catch(SQLException e) {
         System.out.println("error: 등록 오류");
         e.printStackTrace();
      } finally {
         factory.close(conn, pstmt);
      }
     
      return 0;
   }
   
   /**
    * 로그인
    * @param userId 아이디
    * @param userPw 비밀번호
    * @return 로그인 사용자 이름(name), 커플번호(coupleNo) HashMap, 
    *           만약, 아이디없거나, 암호가 틀리면 null 반환 
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
         System.out.println("error: 로그인 오류");
         e.printStackTrace();
      } finally {
         factory.close(conn, pstmt, rs);
      }
      
      return null;
   }
   
   /**
    * 특정 회원 상세 조회 / 내정보조회
    * @param userId 조회 회원 아이디
    * @return 조회회원객체, 아이디가 존재하지 않는 경우 null 반환
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
         System.out.println("error: 회원 상세조회 오류");
         e.printStackTrace();
      } finally {
         factory.close(conn, pstmt, rs);
      }
      
      return null;
   }
   
   /**
    * 아이디 중복 조회
    * @param userId 아이디
    * @return 존재시 true, 미존재시 false
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
         System.out.println("error: 아이디 중복조회 오류");
         e.printStackTrace();
      } finally {
         factory.close(conn, pstmt, rs);
      }
      
      return false;
   }
}