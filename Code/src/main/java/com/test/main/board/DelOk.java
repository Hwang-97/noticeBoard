package com.test.main.board;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/board/delok.do")
public class DelOk extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//할일
		//2. 데이터 가져오기(seq)
		//3. DB 작업 > delete > DAO 위임
		//4. 결과 피드백 > JSP 위임

		//2.
		String seq = req.getParameter("seq");
		
		//3.
		BoardDAO dao = new BoardDAO();
		
		//현재 글에 달린 댓글 삭제하기
		dao.delCommentAll(seq);
		
		int result = dao.del(seq); //1,0
		
		//4.
		req.setAttribute("result", result);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/board/delok.jsp");
		dispatcher.forward(req, resp);
	}

}





