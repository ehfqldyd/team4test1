package work.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import work.model.dto.User;
import work.model.service.UserService;

public class FrontController extends HttpServlet {
	private UserService userService = new UserService();
	// 로그인검증
	// HttpSession session = request.getSession(false);
	// if(session!=null||session.getAttribute("userId")!= null)
	// 관리자검증
	// if(request.getParameter("grade").equals("A"))
	/** 공통기능 회원검증 */
	protected void loginCheck(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			
			request.getRequestDispatcher("login.jsp").forward(request, response);
			// 로그인후 이용해주세요
		}
	}

	/** 관리자검증 */
	protected void adminCheck(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			request.getRequestDispatcher("login.jsp").forward(request, response);
				if(!session.getAttribute("grade").equals("A")){
					request.setAttribute("message", "관리자 권한에서만 가능합니다 ");
					request.getRequestDispatcher("error.jsp").forward(request, response);
			}
		} 
	}
	

	/** 로그인 요청 서비스메서드 */
	protected void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");
		System.out.println("userId:" + userId);
		System.out.println("userPw:" + userPw);
		// 데이터검증 필수입력항목: 아이디,비밀번호 미입력시 오류페이지 이동
		if (userId == null || userPw == null || userId.length() == 0 || userPw.length() == 0) {
			request.setAttribute("message", "로그인정보가없음");
			RequestDispatcher nextView = request.getRequestDispatcher("loginError.jsp");
			nextView.forward(request, response);
		}
		// 모델에게 요청의뢰
		HashMap<String, String> loginMap =userService.login(userId, userPw);
		System.out.println(loginMap);
		if (loginMap != null) {
			request.setAttribute("loginMap", loginMap);
			HttpSession session = request.getSession(true);
			session.setAttribute("userId", userId);
			session.setAttribute("name", loginMap.get("name"));
			session.setAttribute("grade", loginMap.get("grade"));
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else {
			StringBuilder stb = new StringBuilder();
			stb.append("아이디 또는 비밀번호를 다시 확인 하세요.");
			stb.append("<br>");
			stb.append("등록되지 않은 아이디거나 아이디 또는 비밀번호를 잘못입력하셨습니다");
			request.setAttribute("message", stb.toString());
			// 오류페이지로 포워드 이동

			request.getRequestDispatcher("loginError.jsp").forward(request, response);
		}
	}

	/** 회원가입 요청 서비스메서드 */
	protected void join(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");
		String name = request.getParameter("name");
		

		User user = new User(userId, userPw, name);
		if (userId == null || userPw == null || name == null ||   userId.length() == 0
				|| userPw.length() == 0 || name.length() == 0 ) {
			request.setAttribute("message", "가입정보오류");
			RequestDispatcher nextView = request.getRequestDispatcher("error.jsp");
			nextView.forward(request, response);
		}
		// 모델에게 요청의뢰
		int checknum = userService.insertUser(user);
		System.out.println(checknum);
		// 요청결과받아서 응답위한 설정
		if (checknum != 0) {
			request.setAttribute("checknum", checknum);
			request.getRequestDispatcher("login.html").forward(request, response);
		} else {
			StringBuilder stb = new StringBuilder();
			stb.append("가입정보를 다시 확인하세요");
			stb.append("<br>");
			stb.append("잘못입력하셨습니다");
			request.setAttribute("message", stb.toString());
			// 오류페이지로 포워드 이동

			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
		// 응답페이지 이동: 성공, 실패 , 기타
	}


//	
//	/** 암호변경 요청 서비스메서드 */
//	protected void changePassword(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		loginCheck(request,response);
//		String userPw = request.getParameter("userPw");
//		String newUserPw = request.getParameter("newUserPw");
//		// 요청데이터 추출
//		HttpSession session = request.getSession(false);
//			int checknum = userService.updatePw((String) session.getAttribute("userId"), userPw, newUserPw);
//
//			if (checknum != 0) {
//				
//				request.setAttribute("checknum", checknum);
//				session.invalidate();
//				request.getRequestDispatcher("login.jsp").forward(request, response);
//			} else {
//				StringBuilder stb = new StringBuilder();
//				stb.append("가입정보를 다시 확인하세요");
//				stb.append("<br>");
//				stb.append("잘못입력하셨습니다");
//				request.setAttribute("message", stb.toString());
//				request.getRequestDispatcher("error.jsp").forward(request, response);
//			}
//	}

//	/** 로그인 회원의 정보변경 */
//	protected void updateId(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		loginCheck(request,response);
//		HttpSession session = request.getSession(false);
//		String userPw = request.getParameter("userPw");
//		
//		String mobile1 = request.getParameter("mobile1");
//		String mobile2 = request.getParameter("mobile2");
//		String mobile3 = request.getParameter("mobile3");
//		String mobile = mobile1 + "-" + mobile2 + "-" + mobile3;
//		String email1 = request.getParameter("email1");
//		String email2 = request.getParameter("email2");
//		String email = email1 + "@" + email2;
//		String address = request.getParameter("address");
//		// 요청데이터 추출
//
//		if (userPw == null || mobile == null || email == null ||
//
//				userPw.length() == 0  || mobile.length() == 0 || email.length() == 0) {
//
//			// 오류페이지로 이동 : loginerror.jsp
//			// 오류메세지 설정
//			request.setAttribute("message", "입력한 정보 오류 다시확인하세요");
//			// 오류페이지로 포워드 이동
//			RequestDispatcher nextView = request.getRequestDispatcher("error.jsp");
//			nextView.forward(request, response);
//		}
//
//			User dto = userService.updateId((String) session.getAttribute("userId"), userPw, mobile, email,address);
//			if (dto != null) {
//				session.setAttribute("userPw", dto.getUserPw());
//				session.setAttribute("name", dto.getName());
//				session.setAttribute("mobile", dto.getMobile());
//				session.setAttribute("email", dto.getEmail());
//				request.setAttribute("dto", dto);
//				request.getRequestDispatcher("alert.jsp").forward(request, response);
//			} else {
//				StringBuilder stb = new StringBuilder();
//				stb.append("기입정보를 다시 확인해주세요");
//				stb.append("<br>");
//				stb.append("잘못입력하셨습니다");
//				request.setAttribute("message", stb.toString());
//				// 오류페이지로 포워드 이동
//
//				request.getRequestDispatcher("error.jsp").forward(request, response);
//			}
//		}
//	

//	/** 관리자가 회원탈퇴 */
//	protected void memberDelete(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		String userId = request.getParameter("userId");
//		if (userId.trim().length() == 0) {
//			request.setAttribute("message", "삭제 회원의 아이디를 입력해주세요");
//			request.getRequestDispatcher("error.jsp").forward(request, response);
//		}
//
//		int checknum = userService.delete(userId);
//		if (checknum != 0) {
//			request.setAttribute("checknum", checknum);
//			request.getRequestDispatcher("deleteAlert.jsp").forward(request, response);
//		}
//	}

//	/** 로그아웃 요청 서비스메서드 */
//	protected void logout(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		// 요청데이터 추출 1.기존세션객체가져오기
//		loginCheck(request,response);
//		HttpSession session = request.getSession(false);
//		if (session != null || session.getAttribute("userId") != null) {
//			session.removeAttribute("userId");
//			session.removeAttribute("name");
//			session.removeAttribute("grade");
//			session.invalidate();
//			response.sendRedirect("login.jsp");
//		}
//
//		// 2.기존세션객체에서 설정한 속성 존재유무 체크
//		// 3. 속성이존재하면 속성설정 removeattribute
//		// 4.기존세션객체삭제 invalidate()
//		// 5.redirect로 응답뷰로 이동 ->성공,실패
//	}
	
	/** get과 post를 처리해주는 메서드 */
	protected void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 요청파악: 요청데이터에서 요청을 위한 key 데이터가져오기
		String action = request.getParameter("action");

		System.out.println("\n##action : " + action);
		if (action != null) {
			switch (action) {
			case "login":
				login(request, response);
				break;
			case "join":
				join(request, response);
				break;
			
			default:
			}
		} else {
			// 오류페이지로이동
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		process(request, response);
	}

}
