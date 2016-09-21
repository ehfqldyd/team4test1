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
		return dao.insertUser(user);
		
	}
	
	
}
