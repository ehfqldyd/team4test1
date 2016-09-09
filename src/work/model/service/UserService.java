package work.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import work.model.dao.UserDao;
import work.model.dto.User;
import work.util.Utility;

/**회원관리 시스템에대한 서비스 클래스
 * --회원관리 시스템의 기능들에 대한 business logic 클래스
 * 
 * */

public class UserService {
	/**회원DAO 클래스*/
	private UserDao dao = UserDao.getInstance();
	/**로그인 요청 서비스*/
	public HashMap<String,String> login(String userId, String userPw){
		return dao.login(userId, userPw);
	}
	/**회원등록*/
	public int insertUser(User user){
		
		String grade="G";
		
		user.setGrade(grade);
		
		return dao.insertUser(user);
		
	}
	
	public int delete(String userId){
		return dao.delete(userId);
	}
	
	public User selectOne(String userId){
		
		return dao.selectOne(userId);
	}
	public ArrayList<User> selectAll(){
		return dao.selectAll();
	}
	public ArrayList<User> selectAllinfo(String name){
		return dao.selectAllInfo(name);
	}
	public int updatePw(String userId,String userPw,String newUserPw){
		return dao.updatePw(userId, userPw, newUserPw);
	}
	public User updateId(String userId, String userPw, String mobile, String email ,String address ){
		return dao.updateId(userId, userPw, mobile, email, address);
	}
//	public boolean join2(String userId, String userPw,String name,String mobile,String email){
//		int result = dao.insertMember(userId, userPw, name, mobile, email);
//		if(result ==1){ 
//			return true;
//		} else{
//			return false;
//		}
//	}
}
