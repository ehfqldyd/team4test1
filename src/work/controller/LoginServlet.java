package work.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginServlet extends HttpServlet {
	
	/**
	 * Servlet implementation class LoginServlet
	 */
	private static final long serialVersionUID = 1362033479426974269L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("EUC-KR");
		//요청 데이터 (parameter)추출
		//로그인 요청 name="userId" name="userPw"
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");
		String none = request.getParameter("none");
		//System.out.println("\n## userId:"+userId);
		//System.out.println("\n## userPw:"+userPw);
		
		userId= userId.trim();
		userPw= userPw.trim();
		if(none != null){
			none =none.trim();
		}
		if(userId.length() == 0 || userPw.length() ==0 ){
			request.setAttribute("message", "이메일 글자 수 에러");
			RequestDispatcher nextView = request.getRequestDispatcher("loginError.jsp");
			nextView.forward(request, response);
			//오류 페이지로 loginError.jsp
			
		}else{
			//model 에게 로그인 요청 의뢰
			//요청 결과 받아서 응답위한 설정
			// 응답페이지 이동: 로그인 성공, 로그인 실패
			// 아이디 , 암호인증 => 로그인 성공: "000님 로그인 성공" 응답
			//응답위한 MIME-TYPE 및 한글인코딩 설정 
			//역할 분담이 힘듬 .view 작성하기 핵개힘듬
		//	response.setContentType("text/html;charset=EUC-KR");
		//	PrintWriter out = response.getWriter(); 
			
		//	out.println("<h3>로그인 성공</h3>");
//			out.println(userId+"님 회원 전용 서비스를 이용 하시기 바랍니다.");
//			out.println("</body></html>");
//			out.close();
			
			
			HttpSession session = request.getSession();
			session.setAttribute("userId", userId);
			request.getRequestDispatcher("main.jsp").forward(request, response);
			
			
			
		}
		// 데이터 검증: 아이디, 비밀번호 미입력시 오류 페이지 이동
		//key 가 미존재시에 null 반환
		// 데이터 미입력시에 공백문자열 반환
		
		//로그인 성공: 000님 로그인 성공
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("\n### doGet()call:helloWorld");
	}
	

}
