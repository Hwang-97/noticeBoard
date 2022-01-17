package com.test.main.board;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/board/delcommentok.do")
public class DelCommentOk extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//할일
		//1. 데이터 가져오기(seq, bseq)
		//2. DB 작업 > delete > DAO 위임
		//3. 피드백
		
		//1.
		String seq = req.getParameter("seq"); //삭제할 댓글 번호
		String bseq = req.getParameter("bseq"); //돌아갈 게시물(부모글) 번호
		
		//2.
		BoardDAO dao = new BoardDAO();
		int result = dao.delComment(seq); //1,0
		
		//3.
		if (result == 1) {
			resp.sendRedirect("/code/board/view.do?seq=" + bseq);
		} else {
			PrintWriter writer = resp.getWriter();
			writer.println("<html><body><script>");
			writer.println("alert('failed'); history.back();");
			writer.println("</script></body></html>");
			writer.close();
		}
		
	}

}




















