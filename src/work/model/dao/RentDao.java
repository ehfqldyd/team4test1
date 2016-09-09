package work.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import work.model.dto.Bike;
import work.model.dto.Rent;
import work.model.dto.User;
import work.util.Utility;


/**
 * 대여 관련 기능
 * @author choi
 *
 */
public class RentDao {

	private FactoryDao factory = FactoryDao.getInstance();
	private static RentDao instance = new RentDao();

	private RentDao() {
		// 지우면안됨 싱글톤 패턴위배되지않게 남겨줘야됨
	}

	public static RentDao getInstance() {
		return instance;
	}
	
	
	/**
	 * 대여하기 기능
	 * @param bikeNo 자전거번호 받아
	 * @param userId 빌리는 유저의 아이디 받아서
	 * @param location 위치 받아
	 * @return int 변경후 인트반환
	 */
	
	public int insertRent(int bikeNo,String userId ,String location) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = factory.getConnection(); // 연결시켜줘
			String sql ="select * from rentbike where user_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				if(!rs.getString(4).equals("대여종료")){
					System.out.println("error : 자전거는 한개만 대여가능");
					 return 9;
				}
			}
			rs.close();
			pstmt.close();
			String sql1 = "update bikes set bike_check=? ,location=? where bike_no=?";
			pstmt =conn.prepareStatement(sql1);
			pstmt.setString(1, "대여중");
			pstmt.setString(2, location);
			pstmt.setInt(3, bikeNo);
			pstmt.executeQuery();
			pstmt.close();
			String sql2 = "insert into rentbike values ( seq_rent.nextval  , ? , ? , to_char(sysdate,'yyyymmdd hh24:mi:ss') , to_char(sysdate+1/24,'yyyymmdd hh24:mi:ss'))";
			pstmt = conn.prepareStatement(sql2); 
			pstmt.setInt(1, bikeNo);
			pstmt.setString(2, userId);
			
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error : 자전거는 한개만 가능");
			e.printStackTrace();
		} finally {
			// 자원해제
			factory.close(conn, pstmt);
		}
		return 0;
	}

	/**
	 * 반납하기 기능
	 * @param bikeNo 자전거일련번혼 받아서
	 * @param location 위치 받아서
	 * @return int형 으로 반환
	 */
	public int deleteRent(int bikeNo, String location) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			String sql1 = "update bikes set bike_check=?, location=? where bike_no=?";
			pstmt =conn.prepareStatement(sql1);
			pstmt.setString(1, "대여가능");
			pstmt.setString(2, location);
			pstmt.setInt(3, bikeNo);
			pstmt.executeQuery();
			pstmt.close();
			String sql2 = "update rentbike set rent_start=? where bike_no = ?";
			pstmt = conn.prepareStatement(sql2); // 통로만들어줘
			pstmt.setString(1, "대여종료");
			pstmt.setInt(2, bikeNo);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error : 오류");
			e.printStackTrace();
		} finally {
			// 자원해제
			factory.close(conn, pstmt);
		}
		return 0;
	}
	/**
	 * 대여연장
	 * */
	public int delayRent(int bikeNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			
			String sql2 = "update rentbike set rent_end =to_char(to_date(rent_end,'yyyymmdd hh24:mi:ss')+1/24,'yyyymmdd hh24:mi:ss') where bike_no= ? and rent_start not in('대여종료')";
			pstmt = conn.prepareStatement(sql2); // 통로만들어줘
			pstmt.setInt(1, bikeNo);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error : 오류");
			e.printStackTrace();
		} finally {
			// 자원해제
			factory.close(conn, pstmt);
		}
		return 0;
	}
	
	/**
	 * 렌트전체조회
	 * @return ArrayList<Rent> 렌트리스트
	 */
	public ArrayList<Rent> selectAllRent() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int rentNo = 0;
		int bikeNo = 0;
		String userId = null;
		String rentStart = null;
		String rentEnd = null;
		try {
			conn = factory.getConnection(); // 연결시켜줘 전용통로개설
			String sql = "select * from rentbike ";// ?에맵핑되는 전달값설정
			pstmt = conn.prepareStatement(sql); // 통로만들어줘
			rs = pstmt.executeQuery();

			ArrayList<Rent> list = new ArrayList<Rent>();

			while (rs.next()) {
				rentNo = rs.getInt("rent_no");
				bikeNo = rs.getInt("bike_no");
				userId = rs.getString("user_id");
				rentStart = rs.getString("rent_start");
				rentEnd = rs.getString("rent_end");
				
				list.add(new Rent(rentNo, bikeNo, userId, rentStart,rentEnd));

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
	public ArrayList<Rent> selectOneRent(String userId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Rent> rentlist = new ArrayList<Rent>();
		int bikeNo = 0;
		String rentStart = null;
		String rentEnd=null;

		try {
			conn = factory.getConnection(); // 연결시켜줘 전용통로개설
			String sql = "select * from rentbike where user_id = ? ";// ?에맵핑되는
			// 전달값설정
			pstmt = conn.prepareStatement(sql); // 통로만들어줘
			pstmt.setString(1, userId); // 값설정 통로개설완성
			rs = pstmt.executeQuery();// rs에 담아줘

			while (rs.next()) {
				
				bikeNo = rs.getInt("bike_no");
				userId = rs.getString("user_id");
				rentStart = rs.getString("rent_start");
				rentEnd = rs.getString("rent_end");
				
				rentlist.add(new Rent(bikeNo, userId, rentStart,rentEnd));
				
			}
			// 값설정 통로개설완성
			return rentlist;
			
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
	
}
