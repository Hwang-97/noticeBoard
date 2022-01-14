package com.test.main.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/board/list.do")
public class List extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		//호출
		//1. list.do > 목록보기
		//2. list.do?column=subject&word=테스트 > 검색하기
		String column = req.getParameter("column");
		String word = req.getParameter("word");
		String searchmode = "n";
		
		if ((column == null && word == null) 
				|| (column.equals("") && word.equals(""))) {
			searchmode = "n";
		} else {
			searchmode = "y";
		}
		
		//DTO > HashMap
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("column", column);
		map.put("word", word);
		map.put("searchmode", searchmode);
		
		//할일
		//1. DB 작업 > select > DAO 위임
		//1.5 데이터 조작
		//2. 반환값 전달 + JSP 호출
		
		
		
		//1.
		BoardDAO dao = new BoardDAO();
		ArrayList<BoardDTO> list = dao.list(map);
		
		//1.5
		Calendar now = Calendar.getInstance();
		String strNow = String.format("%tF", now); //"2022-01-13"
		
		for (BoardDTO dto : list) {
			
			//날짜 자르기
			if (dto.getRegdate().startsWith(strNow)) {
				dto.setRegdate(dto.getRegdate().substring(11));
			} else {
				dto.setRegdate(dto.getRegdate().substring(0, 10));
			}
			
			
			//제목이 길면 자르기
			if (dto.getSubject().length() > 20) {
				dto.setSubject(dto.getSubject().substring(0, 20) + "..");
			}
			
			
			//제목에서 검색 중 > 검색어 강조!!
			if (searchmode.equals("y") && column.equals("subject")) {
				
				//안녕하세요. 홍길동입니다.
				//안녕하세요. <span style="">홍길동</span>입니다.
				dto.setSubject(dto.getSubject().replace(word, "<span style='background-color:yellow;color:tomato;'>" + word + "</span>"));
			}
			
		}
		
		
		
		//조회수 증가 제어용 티켓
		HttpSession session = req.getSession();
		
		session.setAttribute("readcount", "n");
		
		
		
		//2.
		req.setAttribute("list", list);
		req.setAttribute("map", map);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		dispatcher.forward(req, resp);
	}

}






