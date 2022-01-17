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
		
		
		
		
		//페이징
		int nowPage = 0;		//현재 페이지 번호
		int totalCount = 0;
		int pageSize = 10;		//한페이지당 출력할 게시물 수
		int totalPage = 0;
		int begin = 0;			//where 시작 위치
		int end = 0;			//where 끝 위치
		int n = 0;
		int loop = 0;
		int blockSize = 10;
		
		//list.do > list.do?page=1
		//list.do?page=3
		
		String page = req.getParameter("page");
		
		if (page == null || page == "") nowPage = 1;
		else nowPage = Integer.parseInt(page);
		
		//nowPage > 현재 보게될 페이지 번호
		//list.do?page=1 > where rnum between 1 and 10
		//list.do?page=2 > where rnum between 11 and 20
		//list.do?page=3 > where rnum between 21 and 30
		begin = ((nowPage - 1) * pageSize) + 1;
		end = begin + pageSize - 1;
		
		map.put("begin", begin + "");
		map.put("end", end + "");
		
		
		
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
		
		
		
		
		//페이지바

		//총 게시물 수? > 164
		//총 페이지 수? > 164 / 10 = 16.4페이지 > 17페이지
		
		totalCount = dao.getTotalCount(map);
		totalPage = (int)Math.ceil((double)totalCount / pageSize);
		
		//System.out.println(totalCount);
		//System.out.println(totalPage);
		
		String pagebar = "";
		
		//페이지 번호 > 루프 > 링크 생성
//		for (int i=1; i<=totalPage; i++) {
//			if (i == nowPage) {
//				pagebar += String.format(" <a href='#!' style='color:tomato;'>%d</a> ", i, i);
//			} else {
//				pagebar += String.format(" <a href='/code/board/list.do?page=%d'>%d</a> ", i, i);
//			}
//			
//		}
		
		
		//list.do?page=1
		//1 2 3 4 5 6 7 8 9 10
		
		//list.do?page=3
		//1 2 3 4 5 6 7 8 9 10
		
		//list.do?page=10
		//1 2 3 4 5 6 7 8 9 10
		
		//list.do?page=11
		//11 12 13 14 15 16 17 18 19 20
		
		loop = 1; //루프변수(while)
		n = ((nowPage - 1) / blockSize) * blockSize + 1; //페이지 번호
		
		
		
		pagebar += "<nav><ul class=\"pagination\">";
		
		
		//이전 10페이지
//		if (n == 1) {
//			pagebar += String.format(" <a href='#!'>[이전 %d페이지]</a> ", blockSize);
//		} else {
//			pagebar += String.format(" <a href='/code/board/list.do?page=%d'>[이전 %d페이지]</a> ", n-1, blockSize);
//		}
		
		if (n == 1) {
			pagebar += String.format("<li class='disabled'><a href='#!' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>");
		} else {
			pagebar += String.format("<li><a href='/code/board/list.do?page=%d' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>", n-1);
		}
		
		
		
		
//		while (!(loop > blockSize || n > totalPage)) {
//			
//			if (n == nowPage) {
//				pagebar += String.format(" <a href='#!' style='color:tomato;'>%d</a> ", n);
//			} else {
//				pagebar += String.format(" <a href='/code/board/list.do?page=%d'>%d</a> ", n, n);
//			}			
//			
//			loop++;
//			n++;
//		}
		
		
		while (!(loop > blockSize || n > totalPage)) {
			
			if (n == nowPage) {
				pagebar += String.format("<li class='active'><a href='#!'>%d</a></li>", n);
			} else {
				pagebar += String.format("<li><a href='/code/board/list.do?page=%d'>%d</a></li>", n, n);
			}			
			
			loop++;
			n++;
		}
		
		
		//다음 10페이지
//		if (n > totalPage) {
//			pagebar += String.format(" <a href='#!'>[다음 %d페이지]</a> ", blockSize);
//		} else {
//			pagebar += String.format(" <a href='/code/board/list.do?page=%d'>[다음 %d페이지]</a> ", n, blockSize);
//		}
		
		if (n > totalPage) {
			pagebar += String.format("<li class='disabled'><a href='#!' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li>");
		} else {
			pagebar += String.format("<li><a href='/code/board/list.do?page=%d' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li>", n);
		}
		
		pagebar += "</ul></nav>";
		
		
		//2.
		req.setAttribute("list", list);
		req.setAttribute("map", map);
		
		req.setAttribute("pagebar", pagebar);
		req.setAttribute("nowPage", nowPage);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		dispatcher.forward(req, resp);
	}

}






