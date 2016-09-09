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

import work.model.dto.Bike;
import work.model.dto.Rent;
import work.model.dto.User;
import work.model.service.BikeService;
import work.model.service.RentService;
import work.model.service.UserService;

public class FrontController extends HttpServlet {
	private UserService userService = new UserService();
	private BikeService bikeService = new BikeService();
	private RentService rentService = new RentService();
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
	
	/**대여검증 */
	protected void rentCheck(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			request.getRequestDispatcher("login.jsp").forward(request, response);
				if(!request.getAttribute("userId").equals(session.getAttribute("userId"))){
					request.setAttribute("message", "자전거대여는 한대만 가능합니다! ");
					request.getRequestDispatcher("rentError.jsp").forward(request, response);
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
		String mobile1 = request.getParameter("mobile1");
		String mobile2 = request.getParameter("mobile2");
		String mobile3 = request.getParameter("mobile3");
		String mobile = mobile1 + "-" + mobile2 + "-" + mobile3;
		String email1 = request.getParameter("email1");
		String email2 = request.getParameter("email2");
		String email = email1 + "@" + email2;
		String address = request.getParameter("address");

		User user = new User(userId, userPw, name, mobile, email ,address);
		if (userId == null || userPw == null || name == null || mobile == null || email == null || userId.length() == 0
				|| userPw.length() == 0 || name.length() == 0 || mobile.length() == 0 || email.length() == 0) {
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
			request.getRequestDispatcher("login.jsp").forward(request, response);
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

	protected void myInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			loginCheck(request,response);
			HttpSession session = request.getSession(false);
			User dto = userService.selectOne((String)session.getAttribute("userId"));
			request.setAttribute("dto", dto);
			request.getRequestDispatcher("memberInfo.jsp").forward(request, response);
	}

	protected void memberDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("userId");
		if (userId.trim().length() == 0) {
			request.setAttribute("message", "조회 회원의 아이디를 입력해주세요");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
		User dto = userService.selectOne(userId);
		request.setAttribute("dto", dto);
		request.getRequestDispatcher("memberInfo.jsp").forward(request, response);

	}

	protected void allInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		adminCheck(request,response);
			ArrayList<User> list = userService.selectAll();
			request.setAttribute("list", list);
			request.getRequestDispatcher("memberAll.jsp").forward(request, response);
	}

	/** 전체회원에서 부분이름 검색 */
	protected void selectAllInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		adminCheck(request,response);
		String name = request.getParameter("name");
			ArrayList<User> list = userService.selectAllinfo(name);
			request.setAttribute("list", list);
			request.getRequestDispatcher("memberAll.jsp").forward(request, response);
	}

	/** 암호변경 요청 서비스메서드 */
	protected void changePassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		loginCheck(request,response);
		String userPw = request.getParameter("userPw");
		String newUserPw = request.getParameter("newUserPw");
		// 요청데이터 추출
		HttpSession session = request.getSession(false);
			int checknum = userService.updatePw((String) session.getAttribute("userId"), userPw, newUserPw);

			if (checknum != 0) {
				
				request.setAttribute("checknum", checknum);
				session.invalidate();
				request.getRequestDispatcher("login.jsp").forward(request, response);
			} else {
				StringBuilder stb = new StringBuilder();
				stb.append("가입정보를 다시 확인하세요");
				stb.append("<br>");
				stb.append("잘못입력하셨습니다");
				request.setAttribute("message", stb.toString());
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
	}

	/** 로그인 회원의 정보변경 */
	protected void updateId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		loginCheck(request,response);
		HttpSession session = request.getSession(false);
		String userPw = request.getParameter("userPw");
		
		String mobile1 = request.getParameter("mobile1");
		String mobile2 = request.getParameter("mobile2");
		String mobile3 = request.getParameter("mobile3");
		String mobile = mobile1 + "-" + mobile2 + "-" + mobile3;
		String email1 = request.getParameter("email1");
		String email2 = request.getParameter("email2");
		String email = email1 + "@" + email2;
		String address = request.getParameter("address");
		// 요청데이터 추출

		if (userPw == null || mobile == null || email == null ||

				userPw.length() == 0  || mobile.length() == 0 || email.length() == 0) {

			// 오류페이지로 이동 : loginerror.jsp
			// 오류메세지 설정
			request.setAttribute("message", "입력한 정보 오류 다시확인하세요");
			// 오류페이지로 포워드 이동
			RequestDispatcher nextView = request.getRequestDispatcher("error.jsp");
			nextView.forward(request, response);
		}

			User dto = userService.updateId((String) session.getAttribute("userId"), userPw, mobile, email,address);
			if (dto != null) {
				session.setAttribute("userPw", dto.getUserPw());
				session.setAttribute("name", dto.getName());
				session.setAttribute("mobile", dto.getMobile());
				session.setAttribute("email", dto.getEmail());
				request.setAttribute("dto", dto);
				request.getRequestDispatcher("alert.jsp").forward(request, response);
			} else {
				StringBuilder stb = new StringBuilder();
				stb.append("기입정보를 다시 확인해주세요");
				stb.append("<br>");
				stb.append("잘못입력하셨습니다");
				request.setAttribute("message", stb.toString());
				// 오류페이지로 포워드 이동

				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
		}
	

	/** 관리자가 회원탈퇴 */
	protected void memberDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("userId");
		if (userId.trim().length() == 0) {
			request.setAttribute("message", "삭제 회원의 아이디를 입력해주세요");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}

		int checknum = userService.delete(userId);
		if (checknum != 0) {
			request.setAttribute("checknum", checknum);
			request.getRequestDispatcher("deleteAlert.jsp").forward(request, response);
		}
	}

	/** 로그아웃 요청 서비스메서드 */
	protected void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 요청데이터 추출 1.기존세션객체가져오기
		loginCheck(request,response);
		HttpSession session = request.getSession(false);
		if (session != null || session.getAttribute("userId") != null) {
			session.removeAttribute("userId");
			session.removeAttribute("name");
			session.removeAttribute("grade");
			session.invalidate();
			response.sendRedirect("login.jsp");
		}

		// 2.기존세션객체에서 설정한 속성 존재유무 체크
		// 3. 속성이존재하면 속성설정 removeattribute
		// 4.기존세션객체삭제 invalidate()
		// 5.redirect로 응답뷰로 이동 ->성공,실패
	}
	/**자전거 - [관리자]
	 */
	protected void insertBike(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		adminCheck(request,response);
		int bikeNo =  Integer.parseInt(request.getParameter("bikeNo"));
		String location = request.getParameter("location");
		

		Bike dto = new Bike(bikeNo, location);
		if (bikeNo==0 || location==null	|| location.length() == 0) {
			request.setAttribute("message", "자전거등록오류");
			RequestDispatcher nextView = request.getRequestDispatcher("error.jsp");
			nextView.forward(request, response);
		}
		// 모델에게 요청의뢰
		int checknum =bikeService.insertBike(dto);
		System.out.println(checknum);
		// 요청결과받아서 응답위한 설정
		if (checknum != 0) {
			request.setAttribute("checknum", checknum);
			request.getRequestDispatcher("insertBike.jsp").forward(request, response);
		} else {
			StringBuilder stb = new StringBuilder();
			stb.append("다시 확인하세요");
			stb.append("<br>");
			stb.append("잘못입력하셨습니다");
			request.setAttribute("message", stb.toString());
			// 오류페이지로 포워드 이동

			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
		// 응답페이지 이동: 성공, 실패 , 기타
	}
	protected void allInfoBike(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			adminCheck(request,response);
			ArrayList<Bike> list = bikeService.selectAllBike();
			request.setAttribute("list", list);
			request.getRequestDispatcher("bikeAll.jsp").forward(request, response);
	}
	protected void bikeDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		adminCheck(request,response);
		int bikeNo = Integer.parseInt(request.getParameter("bikeNo"));
		if (bikeNo == 0) {
			request.setAttribute("message", "조회 회원의 아이디를 입력해주세요");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
		Bike dto = bikeService.selectOneBike(bikeNo);
		request.setAttribute("dto", dto);
		request.getRequestDispatcher("bikeInfo.jsp").forward(request, response);

	}
	protected void selectAllInfoBike(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		adminCheck(request,response);
		String location = request.getParameter("location");
			ArrayList<Bike> list =	bikeService.selectAllBikeLocation(location);
			request.setAttribute("list", list);
			request.getRequestDispatcher("bikeAll.jsp").forward(request, response);
	}
	protected void updateLocation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		adminCheck(request,response);
		int bikeNo = Integer.parseInt(request.getParameter("bikeNo"));
		String location = request.getParameter("location");
		String newLocation = request.getParameter("newLocation");
		// 요청데이터 추출
		HttpSession session = request.getSession(false);
			int checknum = bikeService.updateLocation(bikeNo, location, newLocation);

			if (checknum != 0) {
				request.setAttribute("checknum", checknum);
				request.getRequestDispatcher("bikeAll.jsp").forward(request, response);
			} else {
				StringBuilder stb = new StringBuilder();
				stb.append(" 다시 확인하세요");
				stb.append("<br>");
				stb.append("잘못입력하셨습니다");
				request.setAttribute("message", stb.toString());
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
	}
	/**대여*/
	protected void insertRent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		loginCheck(request,response);
		rentCheck(request,response);
		HttpSession session = request.getSession(false);
		int bikeNo =  Integer.parseInt(request.getParameter("bikeNo"));
		
		String location = request.getParameter("location");
		Rent dto = new Rent(bikeNo);
		if (bikeNo==0 || location==null	|| location.length() == 0) {
			request.setAttribute("message", "렌트 오류");
			RequestDispatcher nextView = request.getRequestDispatcher("error.jsp");
			nextView.forward(request, response);
		}
		int checknum =rentService.insertRent(bikeNo,(String)session.getAttribute("userId"), location);
		System.out.println(checknum);
		
		if (checknum==9) {
			
			request.getRequestDispatcher("rentError.jsp").forward(request, response);
		} else if(checknum != 0 && checknum!= 9){
			request.setAttribute("checknum", checknum);
			request.getRequestDispatcher("Controller?action=allInfoRent").forward(request, response);

		}
		else{
			StringBuilder stb = new StringBuilder();
			stb.append("다시 확인하세요");
			stb.append("<br>");
			stb.append("잘못입력하셨습니다");
			request.setAttribute("message", stb.toString());
			// 오류페이지로 포워드 이동

			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
		
		// 응답페이지 이동: 성공, 실패 , 기타
	}
	protected void allInfoRent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			loginCheck(request,response);
			ArrayList<Bike> list = bikeService.selectAllBike();
			request.setAttribute("list", list);
			request.getRequestDispatcher("rentRequest.jsp").forward(request, response);
	}
	protected void selectAllInfoRent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		loginCheck(request,response);
		String location = request.getParameter("location");
			ArrayList<Bike> list =	bikeService.selectAllBikeLocation(location);
			request.setAttribute("list", list);
			request.getRequestDispatcher("rentRequest.jsp").forward(request, response);
	}
	protected void deleteRent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		loginCheck(request,response);
		HttpSession session = request.getSession(false);
		int bikeNo =  Integer.parseInt(request.getParameter("bikeNo"));
		String location = request.getParameter("location");
		Rent dto = new Rent(bikeNo);
		if (bikeNo==0 || location==null	|| location.length() == 0) {
			request.setAttribute("message", " 오류");
			RequestDispatcher nextView = request.getRequestDispatcher("error.jsp");
			nextView.forward(request, response);
		}
		int checknum =rentService.deleteRent(bikeNo, location);
		System.out.println(checknum);
		
		if (checknum != 0) {
			request.setAttribute("checknum", checknum);
			request.getRequestDispatcher("Controller?action=allInfoRent").forward(request, response);
		} else {
			StringBuilder stb = new StringBuilder();
			stb.append("다시 확인하세요");
			stb.append("<br>");
			stb.append("잘못입력하셨습니다");
			request.setAttribute("message", stb.toString());
			// 오류페이지로 포워드 이동

			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
		
		// 응답페이지 이동: 성공, 실패 , 기타
	}
	
	protected void myRentDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			loginCheck(request,response);
			HttpSession session = request.getSession(false);
			ArrayList<Rent> list = rentService.selectOneRent((String)session.getAttribute("userId"));
			request.setAttribute("list", list);
			request.getRequestDispatcher("myRentDetail.jsp").forward(request, response);
	}
	
	protected void delayRent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			loginCheck(request,response);
			int bikeNo =  Integer.parseInt(request.getParameter("bikeNo"));
			int checknum=rentService.delayRent(bikeNo);
			request.setAttribute("checknum", checknum);
			request.getRequestDispatcher("Controller?action=myRentDetail").forward(request, response);

						
	}
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
			case "myInfo":
				myInfo(request, response);
				break;
			case "memberDetail":
				memberDetail(request, response);
				break;
			case "changePassword":
				changePassword(request, response);
				break;
			case "allInfo":
				allInfo(request, response);
				break;
			case "logout":
				logout(request, response);
				break;
			case "selectAllInfo":
				selectAllInfo(request, response);
				break;
			case "memberDelete":
				memberDelete(request, response);
				break;
			case "updateId":
				updateId(request, response);
				break;
			case "insertBike":
				insertBike(request,response);
				break;
			case "allInfoBike":
				allInfoBike(request,response);
				break;
			case "bikeDetail":
				bikeDetail(request,response);
				break;
			case "selectAllInfoBike":
				selectAllInfoBike(request,response);
				break;
			case "updateLocation":
				updateLocation(request,response);
				break;
			case "allInfoRent":
				allInfoRent(request,response);
				break;
			case "selectAllInfoRent":
				selectAllInfoRent(request,response);
				break;
			case "insertRent":
				insertRent(request,response);
				break;
			case "deleteRent":
				deleteRent(request,response);
				break;
			case "myRentDetail":
				myRentDetail(request,response);
				break;
			case "delayRent":
				delayRent(request,response);
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
