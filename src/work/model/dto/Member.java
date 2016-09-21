/**
 * 
 */
package work.model.dto;

/**
 * ȸ�� ������
 * @author ���ر�
 *
 */
public class Member {

   
   /** userId ȸ�� ���̵� */
   private String userId;
   
   /** userPw ȸ�� ��й�ȣ */
   private String userPw;
   
   /** userName ȸ�� �̸� */
   private String userName;
   
   /** coupleNo Ŀ�� ��ȣ */
   private int coupleNo;
   
   
   /** User �⺻ ������ */
   public Member() {}

   /**
    * @param userId ȸ�� ���̵�
    * @param userPw ȸ�� ��й�ȣ
    * @param userName ȸ�� �̸�
    * @param coupleNo Ŀ�� ��ȣ
    */
   public Member(String userId, String userPw, String userName, int coupleNo) {
      super();
      this.userId = userId;
      this.userPw = userPw;
      this.userName = userName;
      this.coupleNo = coupleNo;
   }

   /**
    * @return userId ȸ�� ���̵� ��ȯ
    */
   public String getUserId() {
      return userId;
   }

   /**
    * @param userId ȸ�� ���̵�
    */
   public void setUserId(String userId) {
      this.userId = userId;
   }

   /**
    * @return userPw ȸ�� ��й�ȣ ��ȯ
    */
   public String getUserPw() {
      return userPw;
   }

   /**
    * @param userPw ȸ�� ��й�ȣ
    */
   public void setUserPw(String userPw) {
      this.userPw = userPw;
   }

   /**
    * @return userName ȸ�� �̸� ��ȯ
    */
   public String getUserName() {
      return userName;
   }

   /**
    * @param userName ȸ�� �̸�
    */
   public void setUserName(String userName) {
      this.userName = userName;
   }

   /**
    * @return coupleNo Ŀ�ù�ȣ ��ȯ
    */
   public int getCoupleNo() {
      return coupleNo;
   }

   /**
    * @param coupleNo Ŀ�ù�ȣ
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