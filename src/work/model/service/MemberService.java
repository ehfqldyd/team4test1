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
   
   
   /**회원DAO 클래스*/
   private MemberDao dao = MemberDao.getInstance();
   
   
   /**
    * @param userId 아이디
    * @param userPw 비밀번호
    * @return 반환
    */
   public HashMap<String, String> login(String userId, String userPw) {
      HashMap<String, String> loginMap = dao.selectLogin(userId, userPw);
      System.out.println("\n## service result : " + loginMap);
      return loginMap;
      
      //return dao.selectLogin(userId, userPw);
   }
   
   /** 회원상세조회, 내정보조회 */
   /**
    * @param userId 아이디
    * @return 반환
    */
   public Member getUser(String userId) {
      Member dto = dao.selectOne(userId);
      //System.out.println("\n## service result : " + dto);
      return dto;
   }

   
   public int join(String userId, String userPw, String userName) {
      
	   int coupleNo = 0;
	   
	   Member user = new Member(userId, userPw, userName, coupleNo);
	   int userAdd = dao.join(user);
      return userAdd;
   }
}