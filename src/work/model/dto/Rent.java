package work.model.dto;

import java.io.Serializable;

import work.util.Utility;

/** DTO  ���������ؼ� ����
 * 1.encapsulation
 * 2. ����ȭ��ü
 * <pre>
 * ȸ�� ������ Ŭ���� 
 * </pre>
 *
 * @author ���ؿ�
 * @version ver.1.0
 * @sinse JDK 1.4
 */
public class Rent implements Serializable{
	
	private int rentNo ;
	private int bikeNo ;
	private String userId;
	private String rentStart;
	private String rentEnd;
	

	
	public Rent(){}
	public Rent(int rentNo, int bikeNo,String userId,String rentStart,String rentEnd){
		this.rentNo = rentNo;
		this.bikeNo = bikeNo;
		this.userId = userId;
		this.rentStart =rentStart;
		this.rentEnd =rentEnd;
	}
	public Rent( int bikeNo,String userId,String rentStart,String rentEnd){
	
		this.bikeNo = bikeNo;
		this.userId = userId;
		this.rentStart =rentStart;
		this.rentEnd =rentEnd;
	}
	public Rent( int bikeNo){
		this.bikeNo = bikeNo;
	}
	public int getRentNo() {
		return rentNo;
	}
	public void setRentNo(int rentNo) {
		this.rentNo = rentNo;
	}
	public int getBikeNo() {
		return bikeNo;
	}
	public void setBikeNo(int bikeNo) {
		this.bikeNo = bikeNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRentStart() {
		return rentStart;
	}
	public void setRentStart(String rentStart) {
		this.rentStart = rentStart;
	}
	public String getRentEnd() {
		return rentEnd;
	}
	public void setRentEnd(String rentEnd) {
		this.rentEnd = rentEnd;
	}
	@Override
	public String toString() {
		return "Rent [rentNo=" + rentNo + ", bikeNo=" + bikeNo + ", userId=" + userId + ", rentStart=" + rentStart
				+ ", rentEnd=" + rentEnd + "]";
	}
	
	
	

}
