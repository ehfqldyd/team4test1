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
public class Bike implements Serializable{
	
	private int bikeNo ;
	private String location;
	private String bikeCheck;
	

	
	public Bike(){}
	public Bike(int bikeNo,String location,String bikeCheck){
		this.bikeNo = bikeNo;
		this.location =location;
		this.bikeCheck =bikeCheck;
	}
	public Bike(int bikeNo,String location){
		this.bikeNo = bikeNo;
		this.location =location;
		
	}
	
	
	

	public int getBikeNo() {
		return bikeNo;
	}
	public void setBikeNo(int bikeNo) {
		this.bikeNo = bikeNo;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getBikeCheck() {
		return bikeCheck;
	}
	public void setBikeCheck(String bikeCheck) {
		this.bikeCheck = bikeCheck;
	}
	
@Override
	public String toString() {
		return "Bike [bikeNo=" + bikeNo + ", location=" + location + ", bikeCheck=" + bikeCheck + "]";
	}
	//	@Override
//	public String toString() {
//		StringBuilder builder = new StringBuilder();
//		builder.append("[ID]"+userId);
//		builder.append(", ");
//		builder.append("[PASS]"+Utility.convertSecureCode(userPw));
//		builder.append(", ");
//		builder.append("[�̸�]"+name);
//		builder.append(", ");
//		builder.append("[����ó]"+mobile);
//		builder.append(", ");
//		builder.append("[����]"+email);
//		builder.append(", ");
//		builder.append("[���]"+grade);
//		builder.append(", ");
//		builder.append("[�ּ�]"+address);
//		return builder.toString();
//	}
	
}
