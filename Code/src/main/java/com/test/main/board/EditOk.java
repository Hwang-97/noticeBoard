package com.test.main.board;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/board/editok.do")
public class EditOk extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//할일
		//1. POST > 인코딩
		//2. 데이터 가져오기(seq, subject, content)
		//3. DB 작업 > update > DAO 위임
		//4. 결과 피드백 > JSP 위임
		
		//1.
		req.setCharacterEncoding("UTF-8");
		
		//2.
		String seq = req.getParameter("seq");
		String subject = req.getParameter("subject");
		String content = req.getParameter("content");
		
		//3.
		BoardDAO dao = new BoardDAO();
		
		BoardDTO dto = new BoardDTO();
		dto.setSeq(seq);
		dto.setSubject(subject);
		dto.setContent(content);
		
		int result = dao.edit(dto); //1,0
		
		//4.
		req.setAttribute("result", result);
		req.setAttribute("seq", seq);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/board/editok.jsp");
		dispatcher.forward(req, resp);
	}

}




