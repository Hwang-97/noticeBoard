package com.test.main.board;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/board/addok.do")
public class AddOk extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//할일
		//1. POST > 인코딩
		//2. 데이터 가져오기(subject, content)
		//3. DB 작업 > insert > DAO 위임
		//4. 결과 피드백 > JSP 위임
		
		//1.
		req.setCharacterEncoding("UTF-8");
		
		//2.
		String subject = req.getParameter("subject");
		String content = req.getParameter("content");
		
		
		BoardDAO dao = new BoardDAO();
		
		//현재 글이 새글인지? 답변글인지?
		String reply = req.getParameter("reply");
		int thread = -1;
		int depth = -1;
		int parentThread = -1;
		int parentDepth = -1;
		
		if (reply != null && !reply.equals("")) {
			//답변글
			parentThread = Integer.parseInt(req.getParameter("thread"));
			parentDepth = Integer.parseInt(req.getParameter("depth"));
		}
		
		if (reply == null || reply.equals("")) {
			//새글
			//a. 현존하는 모든 게시물 중에 가장 큰 thread 값을 찾는다. 그 값에 +1000을 한 값을 새글의 thread값으로 사용한다.
			thread = dao.getMaxThread();
			
			//b. 새글의 depth는 0을 넣는다.
			depth = 0;
			
		} else {
			//답변글
			//a. 현존하는 모든 게시물의 thread값을 대상으로 현재 작성 중인 답변글의 부모글의 thread값보다 작고, 이전 새글의 thread값보다 큰 thread를 모두 찾아서 -1 한다.
			int previousThread = (int)Math.floor((parentThread - 1)/ 1000) * 1000;
			HashMap<String,Integer> map = new HashMap<String,Integer>();
			map.put("parentThread", parentThread);
			map.put("previousThread", previousThread);
			
			dao.updateThread(map);
			
			//b. 현재 답변글의 thread값은 부모글의 thread-1 을 넣는다.
			thread = parentThread - 1;
			
			//c. 현재 답변글의 depth값은 부모글의 depth+1을 넣는다.
			depth = parentDepth + 1;
		}
		
		
		//3.
		
		
		BoardDTO dto = new BoardDTO();
		
		HttpSession session = req.getSession();
		
		dto.setId(session.getAttribute("id").toString());
		dto.setSubject(subject);
		dto.setContent(content);
		dto.setUserip(req.getRemoteAddr());
		
		dto.setThread(thread);
		dto.setDepth(depth);
		
		int result = dao.add(dto); //1, 0
		
		//4.
		req.setAttribute("result", result);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/board/addok.jsp");
		dispatcher.forward(req, resp);
	}

}




