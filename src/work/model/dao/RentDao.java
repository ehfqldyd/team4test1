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
 * �뿩 ���� ���
 * @author choi
 *
 */
public class RentDao {

	private FactoryDao factory = FactoryDao.getInstance();
	private static RentDao instance = new RentDao();

	private RentDao() {
		// �����ȵ� �̱��� ������������ʰ� ������ߵ�
	}

	public static RentDao getInstance() {
		return instance;
	}
	
	
	/**
	 * �뿩�ϱ� ���
	 * @param bikeNo �����Ź�ȣ �޾�
	 * @param userId ������ ������ ���̵� �޾Ƽ�
	 * @param location ��ġ �޾�
	 * @return int ������ ��Ʈ��ȯ
	 */
	
	public int insertRent(int bikeNo,String userId ,String location) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = factory.getConnection(); // ���������
			String sql ="select * from rentbike where user_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				if(!rs.getString(4).equals("�뿩����")){
					System.out.println("error : �����Ŵ� �Ѱ��� �뿩����");
					 return 9;
				}
			}
			rs.close();
			pstmt.close();
			String sql1 = "update bikes set bike_check=? ,location=? where bike_no=?";
			pstmt =conn.prepareStatement(sql1);
			pstmt.setString(1, "�뿩��");
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
			System.out.println("error : �����Ŵ� �Ѱ��� ����");
			e.printStackTrace();
		} finally {
			// �ڿ�����
			factory.close(conn, pstmt);
		}
		return 0;
	}

	/**
	 * �ݳ��ϱ� ���
	 * @param bikeNo �������Ϸù�ȥ �޾Ƽ�
	 * @param location ��ġ �޾Ƽ�
	 * @return int�� ���� ��ȯ
	 */
	public int deleteRent(int bikeNo, String location) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			String sql1 = "update bikes set bike_check=?, location=? where bike_no=?";
			pstmt =conn.prepareStatement(sql1);
			pstmt.setString(1, "�뿩����");
			pstmt.setString(2, location);
			pstmt.setInt(3, bikeNo);
			pstmt.executeQuery();
			pstmt.close();
			String sql2 = "update rentbike set rent_start=? where bike_no = ?";
			pstmt = conn.prepareStatement(sql2); // ��θ������
			pstmt.setString(1, "�뿩����");
			pstmt.setInt(2, bikeNo);
			return pstmt.executeUpdate();
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
	 * �뿩����
	 * */
	public int delayRent(int bikeNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = factory.getConnection();
			
			String sql2 = "update rentbike set rent_end =to_char(to_date(rent_end,'yyyymmdd hh24:mi:ss')+1/24,'yyyymmdd hh24:mi:ss') where bike_no= ? and rent_start not in('�뿩����')";
			pstmt = conn.prepareStatement(sql2); // ��θ������
			pstmt.setInt(1, bikeNo);
			return pstmt.executeUpdate();
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
	 * ��Ʈ��ü��ȸ
	 * @return ArrayList<Rent> ��Ʈ����Ʈ
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
			conn = factory.getConnection(); // ��������� ������ΰ���
			String sql = "select * from rentbike ";// ?�����εǴ� ���ް�����
			pstmt = conn.prepareStatement(sql); // ��θ������
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
	public ArrayList<Rent> selectOneRent(String userId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Rent> rentlist = new ArrayList<Rent>();
		int bikeNo = 0;
		String rentStart = null;
		String rentEnd=null;

		try {
			conn = factory.getConnection(); // ��������� ������ΰ���
			String sql = "select * from rentbike where user_id = ? ";// ?�����εǴ�
			// ���ް�����
			pstmt = conn.prepareStatement(sql); // ��θ������
			pstmt.setString(1, userId); // ������ ��ΰ����ϼ�
			rs = pstmt.executeQuery();// rs�� �����

			while (rs.next()) {
				
				bikeNo = rs.getInt("bike_no");
				userId = rs.getString("user_id");
				rentStart = rs.getString("rent_start");
				rentEnd = rs.getString("rent_end");
				
				rentlist.add(new Rent(bikeNo, userId, rentStart,rentEnd));
				
			}
			// ������ ��ΰ����ϼ�
			return rentlist;
			
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
	
}
