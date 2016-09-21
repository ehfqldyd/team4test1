/**
 * 
 */
package work.model.service;

import java.util.HashMap;

import work.model.dao.MemberDao;
import work.model.dto.Member;

/**
 * @author JHK
 *
 */
public class MemberService {
   
   
   /**ȸ��DAO Ŭ����*/
   private MemberDao dao = MemberDao.getInstance();
   
   
   /**
    * @param userId ���̵�
    * @param userPw ��й�ȣ
    * @return ��ȯ
    */
   public HashMap<String, String> login(String userId, String userPw) {
      HashMap<String, String> loginMap = dao.selectLogin(userId, userPw);
      System.out.println("\n## service result : " + loginMap);
      return loginMap;
      
      //return dao.selectLogin(userId, userPw);
   }
   
   /** ȸ������ȸ, ��������ȸ */
   /**
    * @param userId ���̵�
    * @return ��ȯ
    */
   public Member getUser(String userId) {
      Member dto = dao.selectOne(userId);
      //System.out.println("\n## service result : " + dto);
      return dto;
   }

   
   public int registe(String userId, String userPw, String userName) {
      
	   int coupleNo = 0;
	   
	   Member user = new Member(userId, userPw, userName, coupleNo);
	   int userAdd = dao.registe(user);
      return userAdd;
   }
}