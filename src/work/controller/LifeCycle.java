package work.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class lifeCycle
 */
public class LifeCycle extends HttpServlet {
	public int requestCount;
	/* static block �ʱ�ȭ : Ŭ���� �ε��ÿ� �ڵ����� class area �ε� 
	 * app ������� �޸� ����
	 * 
	 * */
	
	static {
		System.out.println("1.���� Ŭ���� �ε� ���� ");
	}
	/* instance block �ʱ�ȭ
	 * -- ��ü �����Ҷ�����(new) ȣ�� ���� 
	 * -- �����ڿ� �����
	 * --�ƱԸ�Ʈ�� ���� ���� �� ����.  
	 * */
	{
		System.out.println("���� �� ����");
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LifeCycle() {

        System.out.println("2. ���� new instance������ ���� ");
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		//init(config);
        throw new ServletException("init ()�ʱ�ȭ ������ ���� �߻���Ŵ");
		//System.out.println("3. ���� �ʱ�ȭ ����");
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		System.out.println("4.���� �ڿ�����: ���� ���� ���� �� ���� ������ ����");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("3.���� ���� �޼��� - get: ");
		++requestCount;
		System.out.println("��û ī��Ʈ - "+requestCount );
		response.setContentType("text/html;charset=euc-kr");
		PrintWriter out = response.getWriter();
		out.println("<html><head>Mtitle></title></head></body>");
		out.println("<h3>��û ī��Ʈ"+requestCount+"</h3>");
		out.println("</body></html>");
		out.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//post ��û���� ���͵� get ��û ���񽺷� dispatch ��Ŵ
		doGet(request, response);
	}

}
