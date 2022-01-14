package com.test.main.board;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/board/edit.do")
public class Edit extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//할일
		//- edit.do?seq=5
		//1. 데이터 가져오기(seq)
		//2. DB 작업 > select > DAO 위임
		//3. 반환값 전달 + JSP 호출하기
		
		//1.
		String seq = req.getParameter("seq");
		
		//2.
		BoardDAO dao = new BoardDAO();
		
		BoardDTO dto = dao.get(seq);
		
		
		
		
		HttpSession session = req.getSession();
		
		if (session.getAttribute("id") == null 
				|| !session.getAttribute("id").toString().equals(dto.getId())) {
			resp.sendRedirect("/code/member/member.do");
			return;
		}
		
		
		
		
		//3.
		req.setAttribute("dto", dto);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/board/edit.jsp");
		dispatcher.forward(req, resp);
	}

}





