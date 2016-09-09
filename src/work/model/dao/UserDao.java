package work.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


import work.model.dto.User;
import work.util.Utility;

/**
 * 회원 관리 기능
 * @author choi
 *
 */

public class UserDao {

	private FactoryDao factory = FactoryDao.getInstance();
	private static UserDao instance = new UserDao();

	private UserDao() {
		// 지우면안됨 싱글톤 패턴위배되지않게 남겨줘야됨
	}

	public static UserDao getInstance() {
		return instance;
	}
	/**
	 * 회원가입기능 
	 * @param user 객체
	 * @return int형 반환
	 */
	public int insertUser(User user) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String userId = user.getUserId();
		String userPw = user.getUserPw();
		String name = user.getName();
		String mobile = user.getMobile();
		String email = user.getEmail();
		String address = user.getAddress();
		String grade = user.getGrade();
		

		try {
			conn = factory.getConnection(); // 연결시켜줘
			// 통로만들어줘
			String sql = "insert into users values ( ? , ? , ? , ? , ? , ? , ? )";
			pstmt = conn.prepareStatement(sql); // 통로만들어줘
			pstmt.setString(1, userId);
			pstmt.setString(2, userPw);
			pstmt.setString(3, name);
			pstmt.setString(4, mobile);
			pstmt.setString(5, email);
			pstmt.setString(6, address);
			pstmt.setString(7, grade);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error : 등록오류");
			e.printStackTrace();
		} finally {
			// 자원해제
			factory.close(conn, pstmt);
		}
		return 0;
	}

	/** 회원탈퇴기능
	 * @param userId 회원아이디 받고
	 * @param userPw 회원비밀번호 받고
	 * @return int 성공실패시 결과 인트반환
	 */
	public int delete(String userId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = factory.getConnection();
			String sql = "delete users where user_id = ?";
			pstmt = conn.prepareStatement(sql); // 통로만들어줘
			pstmt.setString(1, userId); // 값설정 통로개설완성
						
			return pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error : 삭제오류");
			e.printStackTrace();
		} finally {
			// 자원해제
			factory.close(conn, pstmt);
		}
		return 0;
	}

	

	/** 회원전체조회
	 * @return ArrayList<User> list  리스트 로 회원전체반환
	 */
	public ArrayList<User> selectAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String userId = null;
		String userPw = null;
		String name = null;
		String mobile = null;
		String email = null;
		String grade = null;
		String address = null;
		

		try {
			conn = factory.getConnection(); // 연결시켜줘 전용통로개설
			String sql = "select * from users ";// ?에맵핑되는 전달값설정
			pstmt = conn.prepareStatement(sql); // 통로만들어줘
			rs = pstmt.executeQuery();

			ArrayList<User> list = new ArrayList<User>();

			while (rs.next()) {

				userId = rs.getString("user_id");
				userPw = rs.getString("user_pw");
				name = rs.getString("name");
				mobile = rs.getString("mobile");
				email = rs.getString("email");
				address=rs.getString("address");
				grade = rs.getString("grade");
				
				list.add(new User(userId, userPw, name, mobile, email, address, grade ));

			}
			// sql 실행요청 전용통로이므로 sql구문 지정 하면안됨
			return list;
		} catch (SQLException e) {
			System.out.println("error : 전체조회 오류");
			e.printStackTrace();
		} finally {
			// 자원해제
			factory.close(conn, pstmt, rs);
		}
		return null;
	}

	/**회원상세조회
	 * @param userId 사용자아이디 받아서
	 * @return User 멤버객체 반환
	 */
	public User selectOne(String userId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = new User();
	
		String userPw = null;
		String name =null;
		String email = null;
		String mobile=null;
		String address=null;
		String grade = null;
		

		try {
			conn = factory.getConnection(); // 연결시켜줘 전용통로개설
			String sql = "select * from users where user_id = ? ";// ?에맵핑되는
			// 전달값설정
			pstmt = conn.prepareStatement(sql); // 통로만들어줘
			pstmt.setString(1, userId); // 값설정 통로개설완성
		
			rs = pstmt.executeQuery();// rs에 담아줘

			if (rs.next()) {
				userId = rs.getString(1);
				userPw = rs.getString(2);
				name = rs.getString(3);
				mobile = rs.getString(4);
				email = rs.getString(5);
				address = rs.getString(6);
				grade = rs.getString(7);
				
				user = new User(userId, userPw, name, mobile, email, address, grade);
			}
			// 값설정 통로개설완성
			return user;
			
			// sql 실행요청 전용통로이므로 sql구문 지정 하면안됨
		} catch (SQLException e) {
			System.out.println("error : 상세조회 오류");
			e.printStackTrace();
		} finally {
			// 자원해제
			factory.close(conn, pstmt, rs);
		}
		return null;
	}

	
	/** 회원 아이디로 조회후 나머지 부분변경
	 * 나머지부분 입력
	 * @param userId 사용자아이디
	 * @param userPw 사용자비밀번호
	 * @param name 사용자이름
	 * @param mobile 사용자 전화번호
	 * @param email 사용자 이메일
	 * @param adress 주소
	 * @return 유저 객체
	 */

	public User updateId(String userId, String userPw, String mobile, String email ,String address) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		User dto =null;
		try {
			conn = factory.getConnection(); // 연결시켜줘
			// 전용통로개설

			String sql = "update users set user_pw=?, mobile=?, email=?, address=? where user_id = ?";
			// ?에맵핑되는 전달값설정
			pstmt = conn.prepareStatement(sql); // 통로만들어줘
			pstmt.setString(1, userPw);
			pstmt.setString(2, mobile);
			pstmt.setString(3, email);
			pstmt.setString(4, address);
			pstmt.setString(5, userId);
			pstmt.executeUpdate();
			dto = new User(userId,userPw,mobile,email,address);

			// sql 실행요청 전용통로이므로 sql구문 지정 하면안됨
			return dto;
		} catch (SQLException e) {
			System.out.println("error : 등록오류");
			e.printStackTrace();
		} finally {
			// 자원해제
			factory.close(conn, pstmt);
		}
		return null;
	}

	
	

	/**아이디찾기
	 * @param name
	 * @param mobile
	 * @return String userId
	 */
//	public String findId(String name, String mobile) {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		String userId = null;
//		String userPw = null;
//		String email = null;
//		String grade = null;
//		String entryDate = null;
//		int mileage = 0;
//		String manager = null;
//
//		try {
//			conn = factory.getConnection(); // 연결시켜줘 전용통로개설
//			String sql = "select user_id from members where name = ? and mobile = ? ";// ?에맵핑되는
//			// 전달값설정
//			pstmt = conn.prepareStatement(sql); // 통로만들어줘
//			pstmt.setString(1, name);
//			pstmt.setString(2, mobile);// 값설정 통로개설완성
//			rs = pstmt.executeQuery();// rs에 담아줘
//
//			if (rs.next()) {
//				userId = rs.getString(1);
//				return userId;
//
//			}
//
//			// sql 실행요청 전용통로이므로 sql구문 지정 하면안됨
//		} catch (SQLException e) {
//			System.out.println("error : 아이디찾기 오류");
//			e.printStackTrace();
//		} finally {
//			// 자원해제
//			factory.close(conn, pstmt, rs);
//		}
//		return null;
//	}
//
//	/**비밀번호 찾기
//	 * 랜덤숫자+알파벳 임시암호 생성후 전달
//	 * 
//	 * @param userId
//	 * @param mobile
//	 * @return String tempPw;
//	 */
//	public String findPw(String userId, String mobile) {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		String userPw = null;
//		String tempPw = null;
//		try {
//			conn = factory.getConnection(); // 연결시켜줘 전용통로개설
//			String sql = "update members set user_pw=? where user_id = ? and mobile = ? ";// ?에맵핑되는
//			tempPw = Utility.getSecureCodeNumberAndAlphabet();
//			pstmt = conn.prepareStatement(sql); // 통로만들어줘
//			pstmt.setString(1, tempPw);
//			pstmt.setString(2, userId);
//			pstmt.setString(3, mobile);// 값설정 통로개설완성
//			pstmt.executeUpdate();
//			rs = pstmt.executeQuery();// rs에 담아줘
//			return tempPw;
//			// sql 실행요청 전용통로이므로 sql구문 지정 하면안됨
//		} catch (SQLException e) {
//			System.out.println("error : 비밀번호찾기 오류");
//			e.printStackTrace();
//		} finally {
//			// 자원해제
//			factory.close(conn, pstmt, rs);
//		}
//		return null;
//	}

	/** 로그인
	 * @param userId 사용자아이디
	 * @param userPw 사용자 비밀번호
	 * @return HashMap<String, String> map
	 */
	public HashMap<String, String> login(String userId, String userPw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		HashMap<String, String> map = new HashMap<String, String>();
		ResultSet rs = null;

		try {
			conn = factory.getConnection(); // 연결시켜줘 전용통로개설
			String sql = "select name, grade from users where user_id = ? and user_pw = ? ";// ?에맵핑되는
			// 전달값설정
			pstmt = conn.prepareStatement(sql); // 통로만들어줘
			pstmt.setString(1, userId);
			pstmt.setString(2, userPw);// 값설정 통로개설완성
			rs = pstmt.executeQuery();// rs에 담아줘
			if (rs.next()) {
				map.put("name", rs.getString(1));
				map.put("grade", rs.getString(2));
				return map;
			}
			// sql 실행요청 전용통로이므로 sql구문 지정 하면안됨
		} catch (SQLException e) {
			System.out.println("error :  로그인 오류");
			e.printStackTrace();
		} finally {
			// 자원해제
			factory.close(conn, pstmt, rs);
		}
		return null;
	}

	
	/**비밀번호변경
	 * 새로운비밀번호입력
	 * @param userId 사용자아이디
	 * @param userPw 기존비밀번호
	 * @param newUserPw 새로운 비밀번호
	 * @return int 로 결과 비교
	 */
	public int updatePw(String userId, String userPw, String newUserPw) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = factory.getConnection(); // 연결시켜줘
			// 전용통로개설
			String sql = "update users set user_pw=? where user_id = ? and user_pw = ?";
			// ?에맵핑되는 전달값설정
			pstmt = conn.prepareStatement(sql); // 통로만들어줘
			pstmt.setString(1, newUserPw);
			pstmt.setString(2, userId);
			pstmt.setString(3, userPw);
			pstmt.executeUpdate();

			return 1;

			// sql 실행요청 전용통로이므로 sql구문 지정 하면안됨

		} catch (SQLException e) {
			System.out.println("error : 오류");
			e.printStackTrace();
		} finally {
			// 자원해제
			factory.close(conn, pstmt);
		}
		return 0;
	}

	
	/** 같은아이디가 있는지 검사 
	 * @param userId 
	 * @return
	 */
//	public String isExist(String userId) {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		String name = null;
//
//		try {
//			conn = factory.getConnection(); // 연결시켜줘 전용통로개설
//			String sql = "select name from users where user_id = ?";// ?에맵핑되는
//			// 전달값설정
//			pstmt = conn.prepareStatement(sql); // 통로만들어줘
//			pstmt.setString(1, userId);
//			rs = pstmt.executeQuery();// 쿼리문을 rs에 담아줘
//			if (rs.next()) {
//				name = rs.getString(1);
//			}
//			// 값설정 통로개설완성
//			return name;
//			// sql 실행요청 전용통로이므로 sql구문 지정 하면안됨
//		} catch (SQLException e) {
//			System.out.println("error :  오류");
//			e.printStackTrace();
//		} finally {
//			// 자원해제
//			factory.close(conn, pstmt, rs);
//
//		}
//		return null;
//	}
//
//	
	/**
	 * 전체조회 이름으로부분검색
	 * @param name 으로 이름의 일부분검색
	 * @return 리스트 반환
	 */
	public ArrayList<User> selectAllInfo(String name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<User> list = new ArrayList<User>();
		User user = null;
		String userId = null;
		String userPw = null;
		String mobile = null;
		String email = null;
		String address=null;
		String grade = null;

		try {
			conn = factory.getConnection();// 연결시켜줘 전용통로개설
			String sql = "select * from users where name like ?";// ?에맵핑되는
			// 전달값설정
			pstmt = conn.prepareStatement(sql); // 통로만들어줘
			pstmt.setString(1, "%" + name + "%");
			rs = pstmt.executeQuery();// 쿼리문을 rs에 담아줘

			while (rs.next()) {

				userId = rs.getString("user_id");
				userPw = rs.getString("user_pw");
				name = rs.getString("name");
				mobile = rs.getString("mobile");
				email = rs.getString("email");
				address=rs.getString("address");
				grade = rs.getString("grade");
				list.add(new User(userId, userPw, name, mobile, email, address,grade));
			}
			// 값설정 통로개설완성
			return list;
			// sql 실행요청 전용통로이므로 sql구문 지정 하면안됨
		} catch (SQLException e) {
			System.out.println("error :  오류");
			e.printStackTrace();
		} finally {
			// 자원해제
			factory.close(conn, pstmt, rs);
		}

		return null;
	}

}
