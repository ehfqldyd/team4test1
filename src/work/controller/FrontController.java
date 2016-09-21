package work.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import work.util.Utility;
import work.model.dto.Member;
import work.model.service.MemberService;
/*import work.util.Utility;*/

@WebServlet("/Controller")
public class FrontController extends HttpServlet {
   /**
	 * ȸ������ Service ��ü ����
	 */
	private static final long serialVersionUID = 1L;

   private MemberService userService = new MemberService();
   String AllUserId = null;

   /**
    * ��������ȸ ��û ���� �޼��� -- �α��� ȸ���� ������ ��ȸ -- session ������ �α��� ȸ���� ���̵�
    * @param request ��û
    * @param response ����
    * @throws ServletException ����
    * @throws IOException ����ó��
    */
   protected void myInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // �α��� ��������� ���� : �α��� �����ÿ� ���ǰ�ü �����ؼ� userId, grade, name ����
      HttpSession session = request.getSession(false);
      if (session != null && session.getAttribute("userId") != null) {
         String userId = (String) session.getAttribute("userId");
         Member dto = userService.getUser(userId);
         System.out.println(dto);
         if (dto != null) {
            // ��������ȸ ����
            request.setAttribute("dto", dto);
            request.getRequestDispatcher("myinfo.jsp").forward(request, response);

         } else {
            // ������ ����� : ����ó��
            request.setAttribute("message", "�α��� �� ���񽺸� ����Ͻñ� �ٶ��ϴ�.");
            request.getRequestDispatcher("error/loginError.jsp").forward(request, response);
         }

      } else {
         // ������ ����� : ����ó��
         request.setAttribute("message", "�α��� �� ���񽺸� ����Ͻñ� �ٶ��ϴ�.");
         request.getRequestDispatcher("error/loginError.jsp").forward(request, response);
      }

   }

   /**
    * �α��� ��û ���� �޼���
    * @param request ��û
    * @param response ����
    * @throws ServletException ����
    * @throws IOException ����ó��
    */
   protected void login(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // ��û������ ���� : �α��ο�ûview login.jsp
      String userId = request.getParameter("userId");
      AllUserId = userId;
      String userPw = request.getParameter("userPw");

      System.out.println("userId : " + userId);
      System.out.println("userPw : " + userPw);

      // ��û������ ���� : �ʼ� �Է��׸�
      if (userId == null || userId.trim().length() == 0 || userPw == null || userPw.trim().length() == 0) {
         // �������� �������
         request.setAttribute("message", "�α��� ������ �Է��Ͻñ� �ٶ��ϴ�.");

         // ���Է½� ���� ������ �̵� ó��
         RequestDispatcher nextView = request.getRequestDispatcher("error/loginError.jsp");
         nextView.forward(request, response);
      }

      // Model ��û �Ƿ�
      HashMap<String, String> loginMap = userService.login(userId, userPw);
      System.out.println("\n## controller result : " + loginMap);

      // ��û����޾Ƽ� �������� ����
      if (loginMap != null) {
         // ���������� �̵� : ����
         // �α��� ���� => HttpSession ���� ���� ����
         request.setAttribute("loginMap", loginMap);

         // �α��� ���� : ����� ���� ����
         // HttpSession : �α��� ~ �α׾ƿ�(Ÿ�Ӿƿ�) �Ҷ����� �������� ����(����)
         HttpSession session = request.getSession(); // �⺻ : true
         session.setAttribute("userId", userId);
         session.setAttribute("userName", loginMap.get("userName"));
         session.setAttribute("coupleNo", loginMap.get("coupleNo"));
 
         request.getRequestDispatcher("LoginSuccess.jsp").forward(request, response);
      } else {
         // loginMap�� null : ���̵� ������, ��ȣ Ʋ�����
         // ���������� �̵� : ����
         StringBuilder error = new StringBuilder();
         error.append("���̵� �Ǵ� ��й�ȣ�� �ٽ� Ȯ���ϼ���.");
         error.append("<br>");
         error.append("��ϵ��� ���� ���̵��̰ų�, ���̵� �Ǵ� ��й�ȣ�� �߸� �Է��ϼ̽��ϴ�.");

         request.setAttribute("message", error.toString());
         request.getRequestDispatcher("error/loginError.jsp").forward(request, response);
      }
   }
   
   /**
    * ȸ������ ��û ���� �޼���
    * @param request ��û
    * @param response ����
    * @throws ServletException ����
    * @throws IOException ����ó��
    */
   protected void join(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String userId = request.getParameter("userId");
      String userPw = request.getParameter("userPw");
      String userName = request.getParameter("userName");
      
            if (userId==null || userId.trim().length() == 0 ||
            userPw.trim().length() == 0 ||
            userName.trim().length() == 0) {
         request.setAttribute("message", "ȸ������ �ʼ� �׸��� ��� �Է��Ͻñ� �ٶ��ϴ�.");
         request.getRequestDispatcher("error/error.jsp").forward(request, response);
      } else {
         Member dto = new Member();
         int result = userService.join(userId, userPw, userName);
         System.out.println("userId = "+ userId + ", userPw =" + userPw + ", userName = "+ userName);
         if (result == 1) {
            // ���� ����
            StringBuilder message = new StringBuilder();
            message.append(userName);
            message.append("(");
            message.append(Utility.convertSecureCode(userId, 3));
            message.append(")");
            message.append("�� ȸ�����ԿϷ�Ǿ����ϴ�.");
            message.append("<br>");
            message.append("�α����� ���񽺸� �̿��Ͻñ� �ٶ��ϴ�.");
            request.setAttribute("message", message);
            
            // request.setAttribute("message", name + "��
            // ȸ�����ԿϷ�Ǿ����ϴ�.<br>�α����� ���񽺸� �̿��Ͻñ� �ٶ��ϴ�.");
            System.out.println("ȸ�� ���� �Ϸ�");
            request.getRequestDispatcher("Index.html").forward(request, response);
         } else {
            // ���� ����
        	 System.out.println("ȸ�� ���� ����");
            request.setAttribute("message", "ȸ�������� ���������� ������� �ʾҽ��ϴ�.");
            request.getRequestDispatcher("error/JoinError.jsp").forward(request, response);
         }
      }

   }   
   
   /**
    * �α׾ƿ� ��û ���� �޼���
    * 
    * 1. �������ǰ�ü��������
    * 2. �������Ǽ��� �Ӽ� ����Ȯ�� - userId, userName, coupleNo
    * 3. �����Ӽ� ����
    * 4. �������� ����
    * 5. �����������̵� - ���� : index.jsp - ���� : LogoutError.jsp
    */
   /**
    * @param request ��û
    * @param response ����
    * @throws ServletException ����
    * @throws IOException ����ó��
    */
   protected void logout(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // �α��� ��������� ���� : �α��� �����ÿ� ���ǰ�ü �����ؼ� userId, grade, name ����
      // ���� �α��� ����ڴ� �������ǰ�ü ��ȯ
      // �������� ���� ����ڴ� null ��ȯ
      HttpSession session = request.getSession(false);

      if (session != null && session.getAttribute("userId") != null) {
         // �������� �α��� ���� ���� �����
         // �α��� �����ÿ� �����س��� userId, grade, name ���� ����
         session.removeAttribute("userId");
         session.removeAttribute("userName");
         session.removeAttribute("coupleNo");

         // ���ǰ�ü ����
         session.invalidate();

         // �α׾ƿ� ��û ���� : ���������� �̵�
         response.sendRedirect("Index.jsp");
      } else {
         // ������ ����� : ����ó��
         request.setAttribute("message", "�α��� �� ���񽺸� ����Ͻñ� �ٶ��ϴ�.");
         request.getRequestDispatcher("error/LogoutError.jsp").forward(request, response);
      }
   }

   /**
    * get, post ��û�� ó���ϴ� ���� �޼���
    * @param request ��û
    * @param response ����
    * @throws ServletException ����
    * @throws IOException ����ó��
    */
   protected void process(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // ��û �ľ� : ��û�����Ϳ��� ��û�� ���� key ������ ��������
      String action = request.getParameter("action");

      System.out.println("\n### action : " + action);

      if (action != null) {
         switch (action) {
         case "login":
            login(request, response);
            break;
         case "join":
        	 join(request, response);
            break;
         case "logout":
            logout(request, response);
            break;
         case "myInfo":
            myInfo(request, response);
            break;


         default:
            // �������� �ʴ� ��û ���� ������ �̵�
         }
      } else {
         // �߸��� ��û��� ���� ������ �̵�
      }
   }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
    *      response)
    */ 
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      process(request, response);
   }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
    *      response)
    */
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // ��û��ü�� ���� �ѱ� ���ڵ� ����
      request.setCharacterEncoding("euc-kr");
      process(request, response);
   }
}