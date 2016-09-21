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
	// �α��ΰ���
	// HttpSession session = request.getSession(false);
	// if(session!=null||session.getAttribute("userId")!= null)
	// �����ڰ���
	// if(request.getParameter("grade").equals("A"))
	/** ������ ȸ������ */
	protected void loginCheck(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			
			request.getRequestDispatcher("login.jsp").forward(request, response);
			// �α����� �̿����ּ���
		}
	}

	/** �����ڰ��� */
	protected void adminCheck(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			request.getRequestDispatcher("login.jsp").forward(request, response);
				if(!session.getAttribute("grade").equals("A")){
					request.setAttribute("message", "������ ���ѿ����� �����մϴ� ");
					request.getRequestDispatcher("error.jsp").forward(request, response);
			}
		} 
	}
	
	/**�뿩���� */
	protected void rentCheck(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			request.getRequestDispatcher("login.jsp").forward(request, response);
				if(!request.getAttribute("userId").equals(session.getAttribute("userId"))){
					request.setAttribute("message", "�����Ŵ뿩�� �Ѵ븸 �����մϴ�! ");
					request.getRequestDispatcher("rentError.jsp").forward(request, response);
			}
		} 
	}

	/** �α��� ��û ���񽺸޼��� */
	protected void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");
		System.out.println("userId:" + userId);
		System.out.println("userPw:" + userPw);
		// �����Ͱ��� �ʼ��Է��׸�: ���̵�,��й�ȣ ���Է½� ���������� �̵�
		if (userId == null || userPw == null || userId.length() == 0 || userPw.length() == 0) {
			request.setAttribute("message", "�α�������������");
			RequestDispatcher nextView = request.getRequestDispatcher("loginError.jsp");
			nextView.forward(request, response);
		}
		// �𵨿��� ��û�Ƿ�
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
			stb.append("���̵� �Ǵ� ��й�ȣ�� �ٽ� Ȯ�� �ϼ���.");
			stb.append("<br>");
			stb.append("��ϵ��� ���� ���̵�ų� ���̵� �Ǵ� ��й�ȣ�� �߸��Է��ϼ̽��ϴ�");
			request.setAttribute("message", stb.toString());
			// ������������ ������ �̵�

			request.getRequestDispatcher("loginError.jsp").forward(request, response);
		}
	}

	/** ȸ������ ��û ���񽺸޼��� */
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
			request.setAttribute("message", "������������");
			RequestDispatcher nextView = request.getRequestDispatcher("error.jsp");
			nextView.forward(request, response);
		}
		// �𵨿��� ��û�Ƿ�
		int checknum = userService.insertUser(user);
		System.out.println(checknum);
		// ��û����޾Ƽ� �������� ����
		if (checknum != 0) {
			request.setAttribute("checknum", checknum);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} else {
			StringBuilder stb = new StringBuilder();
			stb.append("���������� �ٽ� Ȯ���ϼ���");
			stb.append("<br>");
			stb.append("�߸��Է��ϼ̽��ϴ�");
			request.setAttribute("message", stb.toString());
			// ������������ ������ �̵�

			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
		// ���������� �̵�: ����, ���� , ��Ÿ
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
			request.setAttribute("message", "��ȸ ȸ���� ���̵� �Է����ּ���");
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

	/** ��üȸ������ �κ��̸� �˻� */
	protected void selectAllInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		adminCheck(request,response);
		String name = request.getParameter("name");
			ArrayList<User> list = userService.selectAllinfo(name);
			request.setAttribute("list", list);
			request.getRequestDispatcher("memberAll.jsp").forward(request, response);
	}

	/** ��ȣ���� ��û ���񽺸޼��� */
	protected void changePassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		loginCheck(request,response);
		String userPw = request.getParameter("userPw");
		String newUserPw = request.getParameter("newUserPw");
		// ��û������ ����
		HttpSession session = request.getSession(false);
			int checknum = userService.updatePw((String) session.getAttribute("userId"), userPw, newUserPw);

			if (checknum != 0) {
				
				request.setAttribute("checknum", checknum);
				session.invalidate();
				request.getRequestDispatcher("login.jsp").forward(request, response);
			} else {
				StringBuilder stb = new StringBuilder();
				stb.append("���������� �ٽ� Ȯ���ϼ���");
				stb.append("<br>");
				stb.append("�߸��Է��ϼ̽��ϴ�");
				request.setAttribute("message", stb.toString());
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
	}

	/** �α��� ȸ���� �������� */
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
		// ��û������ ����

		if (userPw == null || mobile == null || email == null ||

				userPw.length() == 0  || mobile.length() == 0 || email.length() == 0) {

			// ������������ �̵� : loginerror.jsp
			// �����޼��� ����
			request.setAttribute("message", "�Է��� ���� ���� �ٽ�Ȯ���ϼ���");
			// ������������ ������ �̵�
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
				stb.append("���������� �ٽ� Ȯ�����ּ���");
				stb.append("<br>");
				stb.append("�߸��Է��ϼ̽��ϴ�");
				request.setAttribute("message", stb.toString());
				// ������������ ������ �̵�

				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
		}
	

	/** �����ڰ� ȸ��Ż�� */
	protected void memberDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("userId");
		if (userId.trim().length() == 0) {
			request.setAttribute("message", "���� ȸ���� ���̵� �Է����ּ���");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}

		int checknum = userService.delete(userId);
		if (checknum != 0) {
			request.setAttribute("checknum", checknum);
			request.getRequestDispatcher("deleteAlert.jsp").forward(request, response);
		}
	}

	/** �α׾ƿ� ��û ���񽺸޼��� */
	protected void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��û������ ���� 1.�������ǰ�ü��������
		loginCheck(request,response);
		HttpSession session = request.getSession(false);
		if (session != null || session.getAttribute("userId") != null) {
			session.removeAttribute("userId");
			session.removeAttribute("name");
			session.removeAttribute("grade");
			session.invalidate();
			response.sendRedirect("login.jsp");
		}

		// 2.�������ǰ�ü���� ������ �Ӽ� �������� üũ
		// 3. �Ӽ��������ϸ� �Ӽ����� removeattribute
		// 4.�������ǰ�ü���� invalidate()
		// 5.redirect�� ������ �̵� ->����,����
	}
	
	
	
	/** get�� post�� ó�����ִ� �޼��� */
	protected void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��û�ľ�: ��û�����Ϳ��� ��û�� ���� key �����Ͱ�������
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
			
			default:
			}
		} else {
			// �������������̵�
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
