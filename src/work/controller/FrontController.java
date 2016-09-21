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
	 * 회원관리 Service 객체 생성
	 */
	private static final long serialVersionUID = 1L;

   private MemberService userService = new MemberService();
   String AllUserId = null;

   /**
    * 내정보조회 요청 서비스 메서드 -- 로그인 회원의 내정보 조회 -- session 설정된 로그인 회원의 아이디
    * @param request 요청
    * @param response 응답
    * @throws ServletException 서블릿
    * @throws IOException 예외처리
    */
   protected void myInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // 로그인 사용자인지 검증 : 로그인 인증시에 세션객체 생성해서 userId, grade, name 설정
      HttpSession session = request.getSession(false);
      if (session != null && session.getAttribute("userId") != null) {
         String userId = (String) session.getAttribute("userId");
         Member dto = userService.getUser(userId);
         System.out.println(dto);
         if (dto != null) {
            // 내정보조회 성공
            request.setAttribute("dto", dto);
            request.getRequestDispatcher("myinfo.jsp").forward(request, response);

         } else {
            // 비정상 사용자 : 오류처리
            request.setAttribute("message", "로그인 후 서비스를 사용하시기 바랍니다.");
            request.getRequestDispatcher("error/loginError.jsp").forward(request, response);
         }

      } else {
         // 비정상 사용자 : 오류처리
         request.setAttribute("message", "로그인 후 서비스를 사용하시기 바랍니다.");
         request.getRequestDispatcher("error/loginError.jsp").forward(request, response);
      }

   }

   /**
    * 로그인 요청 서비스 메서드
    * @param request 요청
    * @param response 응답
    * @throws ServletException 서블릿
    * @throws IOException 예외처리
    */
   protected void login(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // 요청데이터 추출 : 로그인요청view login.jsp
      String userId = request.getParameter("userId");
      AllUserId = userId;
      String userPw = request.getParameter("userPw");

      System.out.println("userId : " + userId);
      System.out.println("userPw : " + userPw);

      // 요청데이터 검증 : 필수 입력항목
      if (userId == null || userId.trim().length() == 0 || userPw == null || userPw.trim().length() == 0) {
         // 응답위한 결과설정
         request.setAttribute("message", "로그인 정보를 입력하시기 바랍니다.");

         // 미입력시 오류 페이지 이동 처리
         RequestDispatcher nextView = request.getRequestDispatcher("error/loginError.jsp");
         nextView.forward(request, response);
      }

      // Model 요청 의뢰
      HashMap<String, String> loginMap = userService.login(userId, userPw);
      System.out.println("\n## controller result : " + loginMap);

      // 요청결과받아서 응답위한 설정
      if (loginMap != null) {
         // 응답페이지 이동 : 성공
         // 로그인 성공 => HttpSession 으로 변경 예정
         request.setAttribute("loginMap", loginMap);

         // 로그인 성공 : 사용자 인증 성공
         // HttpSession : 로그인 ~ 로그아웃(타임아웃) 할때까지 상태정보 설정(유지)
         HttpSession session = request.getSession(); // 기본 : true
         session.setAttribute("userId", userId);
         session.setAttribute("userName", loginMap.get("userName"));
         session.setAttribute("coupleNo", loginMap.get("coupleNo"));
 
         request.getRequestDispatcher("LoginSuccess.jsp").forward(request, response);
      } else {
         // loginMap이 null : 아이디 미존재, 암호 틀린경우
         // 응답페이지 이동 : 실패
         StringBuilder error = new StringBuilder();
         error.append("아이디 또는 비밀번호를 다시 확인하세요.");
         error.append("<br>");
         error.append("등록되지 않은 아이디이거나, 아이디 또는 비밀번호를 잘못 입력하셨습니다.");

         request.setAttribute("message", error.toString());
         request.getRequestDispatcher("error/loginError.jsp").forward(request, response);
      }
   }
   
   /**
    * 회원가입 요청 서비스 메서드
    * @param request 요청
    * @param response 응답
    * @throws ServletException 서블릿
    * @throws IOException 예외처리
    */
   protected void join(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String userId = request.getParameter("userId");
      String userPw = request.getParameter("userPw");
      String userName = request.getParameter("userName");
      
            if (userId==null || userId.trim().length() == 0 ||
            userPw.trim().length() == 0 ||
            userName.trim().length() == 0) {
         request.setAttribute("message", "회원가입 필수 항목을 모두 입력하시기 바랍니다.");
         request.getRequestDispatcher("error/error.jsp").forward(request, response);
      } else {
         Member dto = new Member();
         int result = userService.join(userId, userPw, userName);
         System.out.println("userId = "+ userId + ", userPw =" + userPw + ", userName = "+ userName);
         if (result == 1) {
            // 가입 성공
            StringBuilder message = new StringBuilder();
            message.append(userName);
            message.append("(");
            message.append(Utility.convertSecureCode(userId, 3));
            message.append(")");
            message.append("님 회원가입완료되었습니다.");
            message.append("<br>");
            message.append("로그인후 서비스를 이용하시기 바랍니다.");
            request.setAttribute("message", message);
            
            // request.setAttribute("message", name + "님
            // 회원가입완료되었습니다.<br>로그인후 서비스를 이용하시기 바랍니다.");
            System.out.println("회원 가입 완료");
            request.getRequestDispatcher("Index.html").forward(request, response);
         } else {
            // 가입 실패
        	 System.out.println("회원 가입 실패");
            request.setAttribute("message", "회원가입이 정상적으로 진행되지 않았습니다.");
            request.getRequestDispatcher("error/JoinError.jsp").forward(request, response);
         }
      }

   }   
   
   /**
    * 로그아웃 요청 서비스 메서드
    * 
    * 1. 기존세션객체가져오기
    * 2. 기존세션설정 속성 유무확인 - userId, userName, coupleNo
    * 3. 설정속성 삭제
    * 4. 기존세션 삭제
    * 5. 응답페이지이동 - 성공 : index.jsp - 실패 : LogoutError.jsp
    */
   /**
    * @param request 요청
    * @param response 응답
    * @throws ServletException 서블릿
    * @throws IOException 예외처리
    */
   protected void logout(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // 로그인 사용자인지 검증 : 로그인 인증시에 세션객체 생성해서 userId, grade, name 설정
      // 기존 로그인 사용자는 기존세션객체 반환
      // 인증받지 않은 사용자는 null 반환
      HttpSession session = request.getSession(false);

      if (session != null && session.getAttribute("userId") != null) {
         // 정상으로 로그인 인증 받은 사용자
         // 로그인 인증시에 설정해놓은 userId, grade, name 정보 삭제
         session.removeAttribute("userId");
         session.removeAttribute("userName");
         session.removeAttribute("coupleNo");

         // 세션객체 종료
         session.invalidate();

         // 로그아웃 요청 성공 : 시작페이지 이동
         response.sendRedirect("Index.jsp");
      } else {
         // 비정상 사용자 : 오류처리
         request.setAttribute("message", "로그인 후 서비스를 사용하시기 바랍니다.");
         request.getRequestDispatcher("error/LogoutError.jsp").forward(request, response);
      }
   }

   /**
    * get, post 요청을 처리하는 서비스 메서드
    * @param request 요청
    * @param response 응답
    * @throws ServletException 서블릿
    * @throws IOException 예외처리
    */
   protected void process(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // 요청 파악 : 요청데이터에서 요청을 위한 key 데이터 가져오기
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
            // 지원하지 않는 요청 오류 페이지 이동
         }
      } else {
         // 잘못된 요청방식 오류 페이지 이동
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
      // 요청객체에 대한 한글 인코딩 설정
      request.setCharacterEncoding("euc-kr");
      process(request, response);
   }
}