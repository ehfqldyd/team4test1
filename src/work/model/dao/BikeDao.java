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
import work.model.dto.User;
import work.util.Utility;

/**
 * 자전거 관련 기능
 * @author choi
 *
 */

public class BikeDao {

	private FactoryDao factory = FactoryDao.getInstance();
	private static BikeDao instance = new BikeDao();

	private BikeDao() {
		// 지우면안됨 싱글톤 패턴위배되지않게 남겨줘야됨
	}

	public static BikeDao getInstance() {
		return instance;
	}
	
	
	/**
	 * 자전거 정보 등록
	 * @param bike 자전거 객체 받아옴
	 * @return int형 반환
	 */
	public int insertBike(Bike bike) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		int bikeNo = bike.getBikeNo();
		String location = bike.getLocation();
		String bikeCheck=bike.getBikeCheck();
		

		try {
			conn = factory.getConnection(); // 연결시켜줘
			// 통로만들어줘
			String sql = "insert into bikes values ( ? , ? , ? )";
			pstmt = conn.prepareStatement(sql); // 통로만들어줘
			pstmt.setInt(1, bikeNo);
			pstmt.setString(2, location);
			pstmt.setString(3, bikeCheck);
			
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

	/**
	 * 자전거삭제
	 * @param bikeNo 자전거일련번호
	 * @return int형 반환값
	 */
	public int deleteBike(int bikeNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = factory.getConnection();
			String sql = "delete bikes where bike_no = ?";
			pstmt = conn.prepareStatement(sql); // 통로만들어줘
			pstmt.setInt(1, bikeNo); // 값설정 통로개설완성
						
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

	

	/**
	 * 자전거 전체조회
	 * @return ArrayList<Bike> 자전거리스트 
	 */
	public ArrayList<Bike> selectAllBike() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int bikeNo = 0;
		String location = null;
		String bikeCheck = null;

		try {
			conn = factory.getConnection(); // 연결시켜줘 전용통로개설
			String sql = "select * from bikes ";// ?에맵핑되는 전달값설정
			pstmt = conn.prepareStatement(sql); // 통로만들어줘
			rs = pstmt.executeQuery();

			ArrayList<Bike> list = new ArrayList<Bike>();

			while (rs.next()) {

				bikeNo = rs.getInt("bike_no");
				location = rs.getString("location");
				bikeCheck = rs.getString("bike_check");
				
				
				list.add(new Bike(bikeNo, location, bikeCheck));

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
	
	/**
	 * 자전거상세조회
	 * @param bikeNo 자전거 일련번호
	 * @return Bike객체
	 */
	public Bike selectOneBike(int bikeNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Bike dto = new Bike();
		String location = null;
		String bikeCheck =null;
		
		try {
			conn = factory.getConnection(); // 연결시켜줘 전용통로개설
			String sql = "select * from bikes where bike_no = ? ";// ?에맵핑되는
			// 전달값설정
			pstmt = conn.prepareStatement(sql); // 통로만들어줘
			pstmt.setInt(1, bikeNo); // 값설정 통로개설완성
		
			rs = pstmt.executeQuery();// rs에 담아줘

			if (rs.next()) {
				bikeNo = rs.getInt(1);
				location = rs.getString(2);
				bikeCheck = rs.getString(3);
				
				
				dto = new Bike(bikeNo, location, bikeCheck);
			}
			// 값설정 통로개설완성
			return dto;
			
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


	/**
	 * 자전거 정보변경
	 * @param bikeNo 자전거 일련번호
	 * @param location	 자전거 위치
	 * @param bikeCheck 자전거대여여부
	 * @return Bike객체
	 */
	public Bike updateIdBike(int bikeNo, String location, String bikeCheck) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Bike dto =null;
		try {
			conn = factory.getConnection(); // 연결시켜줘
			// 전용통로개설

			String sql = "update bikes set  location=?, bike_check=? where bike_no = ?";
			// ?에맵핑되는 전달값설정
			pstmt = conn.prepareStatement(sql); // 통로만들어줘
			pstmt.setString(1, location);
			pstmt.setString(2, bikeCheck);
			pstmt.setInt(3, bikeNo);
			
			pstmt.executeUpdate();
			dto = new Bike(bikeNo,location,bikeCheck);

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

	

	
	/**
	 * 자전거 위치 변경
	 * @param bikeNo 자전거일련번호
	 * @param location 자전거위치
	 * @param newLocation 새로운위치
	 * @return int형 반환값만 반환
	 */
	public int updateLocation(int bikeNo, String location, String newLocation) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = factory.getConnection(); // 연결시켜줘
			// 전용통로개설
			String sql = "update bikes set location=? where bike_no = ? and location = ?";
			// ?에맵핑되는 전달값설정
			pstmt = conn.prepareStatement(sql); // 통로만들어줘
			pstmt.setString(1, newLocation);
			pstmt.setInt(2, bikeNo);
			pstmt.setString(3, location);
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

	/**
	 * 자전거위치 부분검색
	 * @param location 자전거위치 
	 * @return ArrayList<Bike> 리스트형식 위치에 해당하는 자전거 반환
	 */
	public ArrayList<Bike> selectAllBikeLocation(String location) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Bike> list = new ArrayList<Bike>();
		int bikeNo = 0;
		String bikeCheck = null;
		

		try {
			conn = factory.getConnection();// 연결시켜줘 전용통로개설
			String sql = "select * from bikes where location like ?";// ?에맵핑되는
			// 전달값설정
			pstmt = conn.prepareStatement(sql); // 통로만들어줘
			pstmt.setString(1, "%" + location + "%");
			rs = pstmt.executeQuery();// 쿼리문을 rs에 담아줘

			while (rs.next()) {

				bikeNo = rs.getInt("bike_no");
				location = rs.getString("location");
				bikeCheck = rs.getString("bike_check");
				
				list.add(new Bike(bikeNo, location, bikeCheck));
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
