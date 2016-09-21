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
		//��û ������ (parameter)����
		//�α��� ��û name="userId" name="userPw"
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
			request.setAttribute("message", "�̸��� ���� �� ����");
			RequestDispatcher nextView = request.getRequestDispatcher("loginError.jsp");
			nextView.forward(request, response);
			//���� �������� loginError.jsp
			
		}else{
			//model ���� �α��� ��û �Ƿ�
			//��û ��� �޾Ƽ� �������� ����
			// ���������� �̵�: �α��� ����, �α��� ����
			// ���̵� , ��ȣ���� => �α��� ����: "000�� �α��� ����" ����
			//�������� MIME-TYPE �� �ѱ����ڵ� ���� 
			//���� �д��� ���� .view �ۼ��ϱ� �ٰ�����
		//	response.setContentType("text/html;charset=EUC-KR");
		//	PrintWriter out = response.getWriter(); 
			
		//	out.println("<h3>�α��� ����</h3>");
//			out.println(userId+"�� ȸ�� ���� ���񽺸� �̿� �Ͻñ� �ٶ��ϴ�.");
//			out.println("</body></html>");
//			out.close();
			
			
			HttpSession session = request.getSession();
			session.setAttribute("userId", userId);
			request.getRequestDispatcher("main.jsp").forward(request, response);
			
			
			
		}
		// ������ ����: ���̵�, ��й�ȣ ���Է½� ���� ������ �̵�
		//key �� ������ÿ� null ��ȯ
		// ������ ���Է½ÿ� ���鹮�ڿ� ��ȯ
		
		//�α��� ����: 000�� �α��� ����
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("\n### doGet()call:helloWorld");
	}
	

}
