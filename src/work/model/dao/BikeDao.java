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
 * ������ ���� ���
 * @author choi
 *
 */

public class BikeDao {

	private FactoryDao factory = FactoryDao.getInstance();
	private static BikeDao instance = new BikeDao();

	private BikeDao() {
		// �����ȵ� �̱��� ������������ʰ� ������ߵ�
	}

	public static BikeDao getInstance() {
		return instance;
	}
	
	
	/**
	 * ������ ���� ���
	 * @param bike ������ ��ü �޾ƿ�
	 * @return int�� ��ȯ
	 */
	public int insertBike(Bike bike) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		int bikeNo = bike.getBikeNo();
		String location = bike.getLocation();
		String bikeCheck=bike.getBikeCheck();
		

		try {
			conn = factory.getConnection(); // ���������
			// ��θ������
			String sql = "insert into bikes values ( ? , ? , ? )";
			pstmt = conn.prepareStatement(sql); // ��θ������
			pstmt.setInt(1, bikeNo);
			pstmt.setString(2, location);
			pstmt.setString(3, bikeCheck);
			
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

	/**
	 * �����Ż���
	 * @param bikeNo �������Ϸù�ȣ
	 * @return int�� ��ȯ��
	 */
	public int deleteBike(int bikeNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = factory.getConnection();
			String sql = "delete bikes where bike_no = ?";
			pstmt = conn.prepareStatement(sql); // ��θ������
			pstmt.setInt(1, bikeNo); // ������ ��ΰ����ϼ�
						
			return pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error : ��������");
			e.printStackTrace();
		} finally {
			// �ڿ�����
			factory.close(conn, pstmt);
		}
		return 0;
	}

	

	/**
	 * ������ ��ü��ȸ
	 * @return ArrayList<Bike> �����Ÿ���Ʈ 
	 */
	public ArrayList<Bike> selectAllBike() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int bikeNo = 0;
		String location = null;
		String bikeCheck = null;

		try {
			conn = factory.getConnection(); // ��������� ������ΰ���
			String sql = "select * from bikes ";// ?�����εǴ� ���ް�����
			pstmt = conn.prepareStatement(sql); // ��θ������
			rs = pstmt.executeQuery();

			ArrayList<Bike> list = new ArrayList<Bike>();

			while (rs.next()) {

				bikeNo = rs.getInt("bike_no");
				location = rs.getString("location");
				bikeCheck = rs.getString("bike_check");
				
				
				list.add(new Bike(bikeNo, location, bikeCheck));

			}
			// sql �����û ��������̹Ƿ� sql���� ���� �ϸ�ȵ�
			return list;
		} catch (SQLException e) {
			System.out.println("error : ��ü��ȸ ����");
			e.printStackTrace();
		} finally {
			// �ڿ�����
			factory.close(conn, pstmt, rs);
		}
		return null;
	}
	
	/**
	 * �����Ż���ȸ
	 * @param bikeNo ������ �Ϸù�ȣ
	 * @return Bike��ü
	 */
	public Bike selectOneBike(int bikeNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Bike dto = new Bike();
		String location = null;
		String bikeCheck =null;
		
		try {
			conn = factory.getConnection(); // ��������� ������ΰ���
			String sql = "select * from bikes where bike_no = ? ";// ?�����εǴ�
			// ���ް�����
			pstmt = conn.prepareStatement(sql); // ��θ������
			pstmt.setInt(1, bikeNo); // ������ ��ΰ����ϼ�
		
			rs = pstmt.executeQuery();// rs�� �����

			if (rs.next()) {
				bikeNo = rs.getInt(1);
				location = rs.getString(2);
				bikeCheck = rs.getString(3);
				
				
				dto = new Bike(bikeNo, location, bikeCheck);
			}
			// ������ ��ΰ����ϼ�
			return dto;
			
			// sql �����û ��������̹Ƿ� sql���� ���� �ϸ�ȵ�
		} catch (SQLException e) {
			System.out.println("error : ����ȸ ����");
			e.printStackTrace();
		} finally {
			// �ڿ�����
			factory.close(conn, pstmt, rs);
		}
		return null;
	}


	/**
	 * ������ ��������
	 * @param bikeNo ������ �Ϸù�ȣ
	 * @param location	 ������ ��ġ
	 * @param bikeCheck �����Ŵ뿩����
	 * @return Bike��ü
	 */
	public Bike updateIdBike(int bikeNo, String location, String bikeCheck) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Bike dto =null;
		try {
			conn = factory.getConnection(); // ���������
			// ������ΰ���

			String sql = "update bikes set  location=?, bike_check=? where bike_no = ?";
			// ?�����εǴ� ���ް�����
			pstmt = conn.prepareStatement(sql); // ��θ������
			pstmt.setString(1, location);
			pstmt.setString(2, bikeCheck);
			pstmt.setInt(3, bikeNo);
			
			pstmt.executeUpdate();
			dto = new Bike(bikeNo,location,bikeCheck);

			// sql �����û ��������̹Ƿ� sql���� ���� �ϸ�ȵ�
			return dto;
		} catch (SQLException e) {
			System.out.println("error : ��Ͽ���");
			e.printStackTrace();
		} finally {
			// �ڿ�����
			factory.close(conn, pstmt);
		}
		return null;
	}

	

	
	/**
	 * ������ ��ġ ����
	 * @param bikeNo �������Ϸù�ȣ
	 * @param location ��������ġ
	 * @param newLocation ���ο���ġ
	 * @return int�� ��ȯ���� ��ȯ
	 */
	public int updateLocation(int bikeNo, String location, String newLocation) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = factory.getConnection(); // ���������
			// ������ΰ���
			String sql = "update bikes set location=? where bike_no = ? and location = ?";
			// ?�����εǴ� ���ް�����
			pstmt = conn.prepareStatement(sql); // ��θ������
			pstmt.setString(1, newLocation);
			pstmt.setInt(2, bikeNo);
			pstmt.setString(3, location);
			pstmt.executeUpdate();

			return 1;

			// sql �����û ��������̹Ƿ� sql���� ���� �ϸ�ȵ�

		} catch (SQLException e) {
			System.out.println("error : ����");
			e.printStackTrace();
		} finally {
			// �ڿ�����
			factory.close(conn, pstmt);
		}
		return 0;
	}

	/**
	 * ��������ġ �κа˻�
	 * @param location ��������ġ 
	 * @return ArrayList<Bike> ����Ʈ���� ��ġ�� �ش��ϴ� ������ ��ȯ
	 */
	public ArrayList<Bike> selectAllBikeLocation(String location) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Bike> list = new ArrayList<Bike>();
		int bikeNo = 0;
		String bikeCheck = null;
		

		try {
			conn = factory.getConnection();// ��������� ������ΰ���
			String sql = "select * from bikes where location like ?";// ?�����εǴ�
			// ���ް�����
			pstmt = conn.prepareStatement(sql); // ��θ������
			pstmt.setString(1, "%" + location + "%");
			rs = pstmt.executeQuery();// �������� rs�� �����

			while (rs.next()) {

				bikeNo = rs.getInt("bike_no");
				location = rs.getString("location");
				bikeCheck = rs.getString("bike_check");
				
				list.add(new Bike(bikeNo, location, bikeCheck));
			}
			// ������ ��ΰ����ϼ�
			return list;
			// sql �����û ��������̹Ƿ� sql���� ���� �ϸ�ȵ�
		} catch (SQLException e) {
			System.out.println("error :  ����");
			e.printStackTrace();
		} finally {
			// �ڿ�����
			factory.close(conn, pstmt, rs);
		}

		return null;
	}
}
