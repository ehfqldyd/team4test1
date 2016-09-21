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
	/* static block 초기화 : 클래스 로딩시에 자동으로 class area 로딩 
	 * app 종료까지 메모리 상주
	 * 
	 * */
	
	static {
		System.out.println("1.변경 클래스 로딩 수행 ");
	}
	/* instance block 초기화
	 * -- 객체 생성할때마다(new) 호출 수행 
	 * -- 생성자와 비슷함
	 * --아규먼트를 전달 받을 수 없다.  
	 * */
	{
		System.out.println("변경 블럭 수행");
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LifeCycle() {

        System.out.println("2. 변경 new instance생성자 수행 ");
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		//init(config);
        throw new ServletException("init ()초기화 수행중 예외 발생시킴");
		//System.out.println("3. 변경 초기화 수행");
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		System.out.println("4.변경 자원해제: 기존 서비스 변경 및 서비스 중지시 수행");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("3.변경 서비스 메서드 - get: ");
		++requestCount;
		System.out.println("요청 카운트 - "+requestCount );
		response.setContentType("text/html;charset=euc-kr");
		PrintWriter out = response.getWriter();
		out.println("<html><head>Mtitle></title></head></body>");
		out.println("<h3>요청 카운트"+requestCount+"</h3>");
		out.println("</body></html>");
		out.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//post 요청으로 들어와도 get 요청 서비스로 dispatch 시킴
		doGet(request, response);
	}

}
