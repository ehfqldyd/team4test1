/**
 * 
 */
package work.model.dto;

/**
 * 회원 도메인
 * @author 장해궁
 *
 */
public class Member {

   
   /** userId 회원 아이디 */
   private String userId;
   
   /** userPw 회원 비밀번호 */
   private String userPw;
   
   /** userName 회원 이름 */
   private String userName;
   
   /** coupleNo 커플 번호 */
   private int coupleNo;
   
   
   /** User 기본 생성자 */
   public Member() {}

   /**
    * @param userId 회원 아이디
    * @param userPw 회원 비밀번호
    * @param userName 회원 이름
    * @param coupleNo 커플 번호
    */
   public Member(String userId, String userPw, String userName, int coupleNo) {
      super();
      this.userId = userId;
      this.userPw = userPw;
      this.userName = userName;
      this.coupleNo = coupleNo;
   }

   /**
    * @return userId 회원 아이디 반환
    */
   public String getUserId() {
      return userId;
   }

   /**
    * @param userId 회원 아이디
    */
   public void setUserId(String userId) {
      this.userId = userId;
   }

   /**
    * @return userPw 회원 비밀번호 반환
    */
   public String getUserPw() {
      return userPw;
   }

   /**
    * @param userPw 회원 비밀번호
    */
   public void setUserPw(String userPw) {
      this.userPw = userPw;
   }

   /**
    * @return userName 회원 이름 반환
    */
   public String getUserName() {
      return userName;
   }

   /**
    * @param userName 회원 이름
    */
   public void setUserName(String userName) {
      this.userName = userName;
   }

   /**
    * @return coupleNo 커플번호 반환
    */
   public int getCoupleNo() {
      return coupleNo;
   }

   /**
    * @param coupleNo 커플번호
    */
   public void setCoupleNo(int coupleNo) {
      this.coupleNo = coupleNo;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("User [userId=");
      builder.append(userId);
      builder.append(", userPw=");
      builder.append(userPw);
      builder.append(", userName=");
      builder.append(userName);
      builder.append(", coupleNo=");
      builder.append(coupleNo);
      builder.append("]");
      return builder.toString();
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + coupleNo;
      result = prime * result + ((userId == null) ? 0 : userId.hashCode());
      result = prime * result + ((userName == null) ? 0 : userName.hashCode());
      result = prime * result + ((userPw == null) ? 0 : userPw.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      Member other = (Member) obj;
      if (coupleNo != other.coupleNo)
         return false;
      if (userId == null) {
         if (other.userId != null)
            return false;
      } else if (!userId.equals(other.userId))
         return false;
      if (userName == null) {
         if (other.userName != null)
            return false;
      } else if (!userName.equals(other.userName))
         return false;
      if (userPw == null) {
         if (other.userPw != null)
            return false;
      } else if (!userPw.equals(other.userPw))
         return false;
      return true;
   }
}