package com.test.main.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/board/add.do")
public class Add extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		
		if (session.getAttribute("id") == null) {
			resp.sendRedirect("/code/member/member.do");
			return;
		}
		
		
		//할일
		//add.do?reply=1&thread=3000&depth=0
		String reply = req.getParameter("reply");
		String thread = req.getParameter("thread");
		String depth = req.getParameter("depth");
		
		req.setAttribute("reply", reply);
		req.setAttribute("thread", thread);
		req.setAttribute("depth", depth);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/board/add.jsp");
		dispatcher.forward(req, resp);
	}

}




