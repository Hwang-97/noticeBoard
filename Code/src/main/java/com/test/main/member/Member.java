package com.test.main.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/member/member.do")
public class Member extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//할일
		//1. DB 작업 > select > DAO 위임
		//2. 반환값 전달 + JSP 호출하기
		
		//1.
		int count = 0;
		
		HttpSession session = req.getSession();
		
		if (session.getAttribute("id") != null) {
			MemberDAO dao = new MemberDAO();
			count = dao.getCount(session.getAttribute("id").toString());
		}
		
		//2.
		req.setAttribute("count", count);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/member/member.jsp");
		dispatcher.forward(req, resp);
	}

}






