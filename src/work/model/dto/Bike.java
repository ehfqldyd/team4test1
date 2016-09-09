package work.model.dto;

import java.io.Serializable;

import work.util.Utility;

/** DTO  패턴적용해서 설계
 * 1.encapsulation
 * 2. 직렬화객체
 * <pre>
 * 회원 도메인 클래스 
 * </pre>
 *
 * @author 최준원
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
//		builder.append("[이름]"+name);
//		builder.append(", ");
//		builder.append("[연락처]"+mobile);
//		builder.append(", ");
//		builder.append("[메일]"+email);
//		builder.append(", ");
//		builder.append("[등급]"+grade);
//		builder.append(", ");
//		builder.append("[주소]"+address);
//		return builder.toString();
//	}
	
}
