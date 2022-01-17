package com.test.main.board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/board/view.do")
public class View extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		String column = req.getParameter("column");
		String word = req.getParameter("word");
		String searchmode = "n";
		
		if ((column == null && word == null) 
				|| (column.equals("") && word.equals(""))) {
			searchmode = "n";
		} else {
			searchmode = "y";
		}
		
		
		
		
		//할일
		//1. 데이터 가져오기(seq)
		//2. DB 작업 > select > DAO 위임
		//2.5 데이터 조작
		//3. 반환값 전달 + JSP 호출하기
		
		//1.
		String seq = req.getParameter("seq");
		String page = req.getParameter("page");
		
		//2.
		BoardDAO dao = new BoardDAO();
		
		//2.3 조회수 증가
		/*
			List.java -> 클릭 -> View.java
	
			                              readcount++
			List.java -> AddCount.java -> View.java
			             readcount++
			             View.do로 이동
			
			List.java -> View.java
			n	                 cookie or session -> 표시(y,n)
			             n -> readcount++ -> y  
		*/
		HttpSession session = req.getSession();
		
		if (session.getAttribute("readcount") != null
				&& session.getAttribute("readcount").toString().equals("n")) {
			dao.addReadCount(seq);
			session.setAttribute("readcount", "y");
		}
		
		BoardDTO dto = dao.get(seq);
		
		
		//2.5
		//제목과 내용에 들어있는 태그를 비활성화
		dto.setSubject(dto.getSubject().replace("<", "&lt;").replace(">", "&gt;"));
		dto.setContent(dto.getContent().replace("<", "&lt;").replace(">", "&gt;"));
				
		//개행 문자 처리
		dto.setContent(dto.getContent().replace("\r\n", "<br>"));
		
		
		//내용에서 검색 중 > 검색어 강조!!
		if (searchmode.equals("y") && column.equals("content")) {
			
			//안녕하세요. 홍길동입니다.
			//안녕하세요. <span style="">홍길동</span>입니다.
			dto.setContent(dto.getContent().replace(word, "<span style='background-color:yellow;color:tomato;'>" + word + "</span>"));
		}
		
		
		
		
		
		
		
		//2.7
		ArrayList<CommentDTO> clist = dao.listComment(seq);
		
		for (CommentDTO cdto : clist) {
			//댓글 개행문자 처리
			cdto.setContent(cdto.getContent().replace("\r\n", "<br>"));
		}
		
		
		//3.
		req.setAttribute("dto", dto);
		
		req.setAttribute("column", column);
		req.setAttribute("word", word);
		
		req.setAttribute("clist", clist);
		
		req.setAttribute("page", page);
		

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/board/view.jsp");
		dispatcher.forward(req, resp);
	}

}



