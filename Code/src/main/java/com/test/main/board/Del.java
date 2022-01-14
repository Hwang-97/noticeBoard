package com.test.main.board;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/board/del.do")
public class Del extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//할일
		//- del.do?seq=5
		//1. 데이터 가져오기(seq)
		//2. seq 전달 + JSP 호출하기
		
		//1.
		String seq = req.getParameter("seq");
		
		
		BoardDAO dao = new BoardDAO();
		BoardDTO dto = dao.get(seq);
		HttpSession session = req.getSession();
		
		if (session.getAttribute("id") == null 
				|| !session.getAttribute("id").toString().equals(dto.getId())) {
			resp.sendRedirect("/code/member/member.do");
			return;
		}
		
		
		//2.
		req.setAttribute("seq", seq);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/board/del.jsp");
		dispatcher.forward(req, resp);
	}

}





