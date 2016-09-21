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
public class User implements Serializable{
	
	private String userId ;
	private String userPw;
	private String name;
	private String mobile;
	private String email;
	private String address;
	private String grade;

	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public User(){}
	public User(String userId,String userPw,String name){
		this.userId = userId;
		this.userPw =userPw;
		this.name =name;
		
	}
	public User(String userId, String userPw, String mobile, String email,String address){
		this.userId = userId;
		this.userPw =userPw;
		this.mobile=mobile;
		this.email =email;
		this.address =address;
	}
	
	public User(String userId,String userPw, String name,String mobile,String email,String address ,String grade){
		this.userId = userId;
		this.userPw =userPw;
		this.name = name;
		this.mobile=mobile;
		this.email =email;
		this.address = address;
		this.grade = grade;
		
	}
	
	@Override
	public int hashCode() {
		
		return userId.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[ID]"+userId);
		builder.append(", ");
		builder.append("[PASS]"+Utility.convertSecureCode(userPw));
		builder.append(", ");
		builder.append("[�̸�]"+name);
		builder.append(", ");
		builder.append("[����ó]"+mobile);
		builder.append(", ");
		builder.append("[����]"+email);
		builder.append(", ");
		builder.append("[���]"+grade);
		builder.append(", ");
		builder.append("[�ּ�]"+address);
		return builder.toString();
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
}
