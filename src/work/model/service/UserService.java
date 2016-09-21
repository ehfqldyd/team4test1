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
		return dao.insertUser(user);
		
	}
	
	
}
