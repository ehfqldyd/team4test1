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
 * ȸ�� ���� ���
 * @author choi
 *
 */

public class UserDao {

	private FactoryDao factory = FactoryDao.getInstance();
	private static UserDao instance = new UserDao();

	private UserDao() {
		// �����ȵ� �̱��� ������������ʰ� ������ߵ�
	}

	public static UserDao getInstance() {
		return instance;
	}
	/**
	 * ȸ�����Ա�� 
	 * @param user ��ü
	 * @return int�� ��ȯ
	 */
	public int insertUser(User user) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String userId = user.getUserId();
		String userPw = user.getUserPw();
		String name = user.getName();
		
		

		try {
			conn = factory.getConnection(); // ���������
			// ��θ������
			String sql = "insert into member values ( ? , ? , ? , 0 )";
			pstmt = conn.prepareStatement(sql); // ��θ������
			pstmt.setString(1, userId);
			pstmt.setString(2, userPw);
			pstmt.setString(3, name);
			
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error : ��Ͽ���");
			e.printStackTrace();
		} finally {
			// �ڿ�����
			factory.close(conn, pstmt);
		}
		return 0;
	}

//	/** ȸ��Ż����
//	 * @param userId ȸ�����̵� �ް�
//	 * @param userPw ȸ����й�ȣ �ް�
//	 * @return int �������н� ��� ��Ʈ��ȯ
//	 */
//	public int delete(String userId) {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			conn = factory.getConnection();
//			String sql = "delete users where user_id = ?";
//			pstmt = conn.prepareStatement(sql); // ��θ������
//			pstmt.setString(1, userId); // ������ ��ΰ����ϼ�
//						
//			return pstmt.executeUpdate();
//			
//		} catch (SQLException e) {
//			System.out.println("error : ��������");
//			e.printStackTrace();
//		} finally {
//			// �ڿ�����
//			factory.close(conn, pstmt);
//		}
//		return 0;
//	}
//
//	
//
//	/** ȸ����ü��ȸ
//	 * @return ArrayList<User> list  ����Ʈ �� ȸ����ü��ȯ
//	 */
//	public ArrayList<User> selectAll() {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		String userId = null;
//		String userPw = null;
//		String name = null;
//		String mobile = null;
//		String email = null;
//		String grade = null;
//		String address = null;
//		
//
//		try {
//			conn = factory.getConnection(); // ��������� ������ΰ���
//			String sql = "select * from users ";// ?�����εǴ� ���ް�����
//			pstmt = conn.prepareStatement(sql); // ��θ������
//			rs = pstmt.executeQuery();
//
//			ArrayList<User> list = new ArrayList<User>();
//
//			while (rs.next()) {
//
//				userId = rs.getString("user_id");
//				userPw = rs.getString("user_pw");
//				name = rs.getString("name");
//				mobile = rs.getString("mobile");
//				email = rs.getString("email");
//				address=rs.getString("address");
//				grade = rs.getString("grade");
//				
//				list.add(new User(userId, userPw, name, mobile, email, address, grade ));
//
//			}
//			// sql �����û ��������̹Ƿ� sql���� ���� �ϸ�ȵ�
//			return list;
//		} catch (SQLException e) {
//			System.out.println("error : ��ü��ȸ ����");
//			e.printStackTrace();
//		} finally {
//			// �ڿ�����
//			factory.close(conn, pstmt, rs);
//		}
//		return null;
//	}
//
//	/**ȸ������ȸ
//	 * @param userId ����ھ��̵� �޾Ƽ�
//	 * @return User �����ü ��ȯ
//	 */
//	public User selectOne(String userId) {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		User user = new User();
//	
//		String userPw = null;
//		String name =null;
//		String email = null;
//		String mobile=null;
//		String address=null;
//		String grade = null;
//		
//
//		try {
//			conn = factory.getConnection(); // ��������� ������ΰ���
//			String sql = "select * from users where user_id = ? ";// ?�����εǴ�
//			// ���ް�����
//			pstmt = conn.prepareStatement(sql); // ��θ������
//			pstmt.setString(1, userId); // ������ ��ΰ����ϼ�
//		
//			rs = pstmt.executeQuery();// rs�� �����
//
//			if (rs.next()) {
//				userId = rs.getString(1);
//				userPw = rs.getString(2);
//				name = rs.getString(3);
//				mobile = rs.getString(4);
//				email = rs.getString(5);
//				address = rs.getString(6);
//				grade = rs.getString(7);
//				
//				user = new User(userId, userPw, name, mobile, email, address, grade);
//			}
//			// ������ ��ΰ����ϼ�
//			return user;
//			
//			// sql �����û ��������̹Ƿ� sql���� ���� �ϸ�ȵ�
//		} catch (SQLException e) {
//			System.out.println("error : ����ȸ ����");
//			e.printStackTrace();
//		} finally {
//			// �ڿ�����
//			factory.close(conn, pstmt, rs);
//		}
//		return null;
//	}


	
	

	/**���̵�ã��
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
//			conn = factory.getConnection(); // ��������� ������ΰ���
//			String sql = "select user_id from members where name = ? and mobile = ? ";// ?�����εǴ�
//			// ���ް�����
//			pstmt = conn.prepareStatement(sql); // ��θ������
//			pstmt.setString(1, name);
//			pstmt.setString(2, mobile);// ������ ��ΰ����ϼ�
//			rs = pstmt.executeQuery();// rs�� �����
//
//			if (rs.next()) {
//				userId = rs.getString(1);
//				return userId;
//
//			}
//
//			// sql �����û ��������̹Ƿ� sql���� ���� �ϸ�ȵ�
//		} catch (SQLException e) {
//			System.out.println("error : ���̵�ã�� ����");
//			e.printStackTrace();
//		} finally {
//			// �ڿ�����
//			factory.close(conn, pstmt, rs);
//		}
//		return null;
//	}
//
//	/**��й�ȣ ã��
//	 * ��������+���ĺ� �ӽþ�ȣ ������ ����
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
//			conn = factory.getConnection(); // ��������� ������ΰ���
//			String sql = "update members set user_pw=? where user_id = ? and mobile = ? ";// ?�����εǴ�
//			tempPw = Utility.getSecureCodeNumberAndAlphabet();
//			pstmt = conn.prepareStatement(sql); // ��θ������
//			pstmt.setString(1, tempPw);
//			pstmt.setString(2, userId);
//			pstmt.setString(3, mobile);// ������ ��ΰ����ϼ�
//			pstmt.executeUpdate();
//			rs = pstmt.executeQuery();// rs�� �����
//			return tempPw;
//			// sql �����û ��������̹Ƿ� sql���� ���� �ϸ�ȵ�
//		} catch (SQLException e) {
//			System.out.println("error : ��й�ȣã�� ����");
//			e.printStackTrace();
//		} finally {
//			// �ڿ�����
//			factory.close(conn, pstmt, rs);
//		}
//		return null;
//	}

	/** �α���
	 * @param userId ����ھ��̵�
	 * @param userPw ����� ��й�ȣ
	 * @return HashMap<String, String> map
	 */
	public HashMap<String, String> login(String userId, String userPw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		HashMap<String, String> map = new HashMap<String, String>();
		ResultSet rs = null;

		try {
			conn = factory.getConnection(); // ��������� ������ΰ���
			String sql = "select name, grade from users where user_id = ? and user_pw = ? ";// ?�����εǴ�
			// ���ް�����
			pstmt = conn.prepareStatement(sql); // ��θ������
			pstmt.setString(1, userId);
			pstmt.setString(2, userPw);// ������ ��ΰ����ϼ�
			rs = pstmt.executeQuery();// rs�� �����
			if (rs.next()) {
				map.put("name", rs.getString(1));
				map.put("grade", rs.getString(2));
				return map;
			}
			// sql �����û ��������̹Ƿ� sql���� ���� �ϸ�ȵ�
		} catch (SQLException e) {
			System.out.println("error :  �α��� ����");
			e.printStackTrace();
		} finally {
			// �ڿ�����
			factory.close(conn, pstmt, rs);
		}
		return null;
	}

//	
//	/**��й�ȣ����
//	 * ���ο��й�ȣ�Է�
//	 * @param userId ����ھ��̵�
//	 * @param userPw ������й�ȣ
//	 * @param newUserPw ���ο� ��й�ȣ
//	 * @return int �� ��� ��
//	 */
//	public int updatePw(String userId, String userPw, String newUserPw) {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//
//		try {
//			conn = factory.getConnection(); // ���������
//			// ������ΰ���
//			String sql = "update users set user_pw=? where user_id = ? and user_pw = ?";
//			// ?�����εǴ� ���ް�����
//			pstmt = conn.prepareStatement(sql); // ��θ������
//			pstmt.setString(1, newUserPw);
//			pstmt.setString(2, userId);
//			pstmt.setString(3, userPw);
//			pstmt.executeUpdate();
//
//			return 1;
//
//			// sql �����û ��������̹Ƿ� sql���� ���� �ϸ�ȵ�
//
//		} catch (SQLException e) {
//			System.out.println("error : ����");
//			e.printStackTrace();
//		} finally {
//			// �ڿ�����
//			factory.close(conn, pstmt);
//		}
//		return 0;
//	}

	
	/** �������̵� �ִ��� �˻� 
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
//			conn = factory.getConnection(); // ��������� ������ΰ���
//			String sql = "select name from users where user_id = ?";// ?�����εǴ�
//			// ���ް�����
//			pstmt = conn.prepareStatement(sql); // ��θ������
//			pstmt.setString(1, userId);
//			rs = pstmt.executeQuery();// �������� rs�� �����
//			if (rs.next()) {
//				name = rs.getString(1);
//			}
//			// ������ ��ΰ����ϼ�
//			return name;
//			// sql �����û ��������̹Ƿ� sql���� ���� �ϸ�ȵ�
//		} catch (SQLException e) {
//			System.out.println("error :  ����");
//			e.printStackTrace();
//		} finally {
//			// �ڿ�����
//			factory.close(conn, pstmt, rs);
//
//		}
//		return null;
//	}
//
//	


}
