package work.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import work.model.dao.UserDao;
import work.model.dto.User;
import work.util.Utility;

/**ȸ������ �ý��ۿ����� ���� Ŭ����
 * --ȸ������ �ý����� ��ɵ鿡 ���� business logic Ŭ����
 * 
 * */

public class UserService {
	/**ȸ��DAO Ŭ����*/
	private UserDao dao = UserDao.getInstance();
	/**�α��� ��û ����*/
	public HashMap<String,String> login(String userId, String userPw){
		return dao.login(userId, userPw);
	}
	/**ȸ�����*/
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
