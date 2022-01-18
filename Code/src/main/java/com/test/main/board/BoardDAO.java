package com.test.main.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.bootstrap.DOMImplementationRegistry;

import com.test.jdbc.DBUtil;


//*** DAO에서는 데이터 조작하지 않는다. 데이터 입출력만 한다.
public class BoardDAO {

	private Connection conn;
	private Statement stat; //매개변수X
	private PreparedStatement pstat; //매개변수O
	private ResultSet rs;
	
	public BoardDAO() {
		
		try {

			conn = DBUtil.open();
			stat = conn.createStatement();

		} catch (Exception e) {
			System.out.println("BoardDAO.BoardDAO()");
			e.printStackTrace();
		}
		
	}

	public int add(BoardDTO dto) {
		
		try {

			String sql = "insert into tblBoard (seq, id, subject, content, regdate, readcount, userip, thread, depth) values (seqBoard.nextVal, ?, ?, ?, default, default, ?, ?, ?)";
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, dto.getId());      //X
			pstat.setString(2, dto.getSubject()); //O
			pstat.setString(3, dto.getContent()); //O
			pstat.setString(4, dto.getUserip());  //X
			
			pstat.setInt(5, dto.getThread());
			pstat.setInt(6, dto.getDepth());
			
			return pstat.executeUpdate();
			
			
//			String sql = String.format("insert into tblBoard (seq, id, subject, content, regdate, readcount, userip) values (seqBoard.nextVal, '%s', '%s', '%s', default, default, '%s')", dto.getId(), dto.getSubject().replace("'", "''"), dto.getContent().replace("'", "''"), dto.getUserip());
//			
//			return stat.executeUpdate(sql);

		} catch (Exception e) {
			System.out.println("BoardDAO.add()");
			e.printStackTrace();
		}
		
		return 0;
	}

	
	//List 서블릿이 목록주세요~ 라고 요청
	public ArrayList<BoardDTO> list(HashMap<String, String> map) {
		
		try {

			String where = "";
			
			if (map.get("searchmode").equals("y")) {
				where = String.format("where %s like '%%%s%%'"
								, map.get("column")
								, map.get("word").replace("'", "''"));
			}
			
			String sql = String.format("select * from (select rownum as rnum, a.* from (select * from vwBoard %s order by thread desc) a) where rnum between %s and %s order by thread desc", where, map.get("begin"), map.get("end"));
			
			rs = stat.executeQuery(sql);
			
			ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
			
			while (rs.next()) {
				//레코드 1줄 -> BoardDTO 1개
				BoardDTO dto = new BoardDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setSubject(rs.getString("subject"));
				dto.setName(rs.getString("name"));
				dto.setId(rs.getString("id"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setReadcount(rs.getInt("readcount"));
				
				dto.setIsnew(rs.getDouble("isnew"));
				
				dto.setCommentcount(rs.getInt("commentcount"));		
				
				dto.setDepth(rs.getInt("depth"));
				
				list.add(dto);
			}
			
			return list;

		} catch (Exception e) {
			System.out.println("BoardDAO.list()");
			e.printStackTrace();
		}
		
		return null;
	}

	//View 서블릿이 글번호를 줄테니 레코드(DTO)를 주세요~
	public BoardDTO get(String seq) {
		
		try {

			String sql = "select tblBoard.*, (select name from tblUser where id = tblBoard.id) as name from tblBoard where seq = ?";
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, seq);
			
			rs = pstat.executeQuery();
			
			if (rs.next()) {
				BoardDTO dto = new BoardDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setReadcount(rs.getInt("readcount"));
				dto.setUserip(rs.getString("userip"));
				
				dto.setThread(rs.getInt("thread"));
				dto.setDepth(rs.getInt("depth"));
				
				return dto;
			}

		} catch (Exception e) {
			System.out.println("BoardDAO.get()");
			e.printStackTrace();
		}
		
		return null;
	}

	//View 서블릿이 글번호를 줄테니 조회수 +1 해주세요~
	public void addReadCount(String seq) {
		
		try {

			String sql = "update tblBoard set readcount = readcount + 1 where seq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			pstat.executeUpdate();

		} catch (Exception e) {
			System.out.println("BoardDAO.addReadCount()");
			e.printStackTrace();
		}		
		
	}

	//EditOk 서블릿이 DTO를 줄테니 수정해주세요~
	public int edit(BoardDTO dto) {

		try {

			String sql = "update tblBoard set subject = ?, content = ? where seq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getSubject());
			pstat.setString(2, dto.getContent());
			pstat.setString(3, dto.getSeq());
			
			return pstat.executeUpdate();

		} catch (Exception e) {
			System.out.println("BoardDAO.edit()");
			e.printStackTrace();
		}
		
		return 0;
	}

	//DelOk 서블릿이 글번호를 줄테니 글을 삭제주세요~
	public int del(String seq) {
		
		try {

			String sql = "delete from tblBoard where seq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			return pstat.executeUpdate();

		} catch (Exception e) {
			System.out.println("BoardDAO.del()");
			e.printStackTrace();
		}
		
		return 0;
	}

	
	//AddCommentOk 서블릿이 CommentDTO를 줄테니 insert해주세요~
	public int addComment(CommentDTO cdto) {
		
		try {

			String sql = "insert into tblComment (seq, id, content, regdate, bseq) values (seqComment.nextVal, ?, ?, default, ?)";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, cdto.getId());
			pstat.setString(2, cdto.getContent());
			pstat.setString(3, cdto.getBseq());
			
			return pstat.executeUpdate();

		} catch (Exception e) {
			System.out.println("BoardDAO.addComment()");
			e.printStackTrace();
		}
		
		return 0;
	}

	
	//View 서블릿이 글번호를 줄테니 그 글에 딸린 댓글 목록을 주세요~
	public ArrayList<CommentDTO> listComment(String seq) {
		
		try {

			String sql = "select tblComment.*, (select name from tblUser where id = tblComment.id) as name from tblComment where bseq = ? order by seq desc";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			rs = pstat.executeQuery();
			
			ArrayList<CommentDTO> clist = new ArrayList<CommentDTO>();
			
			while (rs.next()) {
				//레코드 1줄 > DTO 1개
				CommentDTO dto = new CommentDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setContent(rs.getString("content"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setBseq(rs.getString("bseq"));
				
				clist.add(dto);
			}
			
			return clist;

		} catch (Exception e) {
			System.out.println("BoardDAO.listComment()");
			e.printStackTrace();
		}
		
		return null;
	}

	//DelCommentOk 서블릿이 댓글 번호를 줄테니 삭제해주세요~
	public int delComment(String seq) {
		
		try {

			String sql = "delete from tblComment where seq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			return pstat.executeUpdate();

		} catch (Exception e) {
			System.out.println("BoardDAO.delComment()");
			e.printStackTrace();
		}
		
		return 0;
	}

	
	//DelOk 서블릿이 부모 글번호를 줄테니 달린 댓글을 모두 삭제해주세요~
	public void delCommentAll(String seq) {
		
		try {

			String sql = "delete from tblComment where bseq = ?";
			
			//cascade > X
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			pstat.executeUpdate();			

		} catch (Exception e) {
			System.out.println("BoardDAO.delCommentAll()");
			e.printStackTrace();
		}
		
	}

	
	//List 서블릿이 게시물이 몇개인지 알려주세요~
	public int getTotalCount(HashMap<String, String> map) {
		
		try {

			String where = "";
			
			if (map.get("searchmode").equals("y")) {
				where = String.format("where %s like '%%%s%%'"
								, map.get("column")
								, map.get("word").replace("'", "''"));
			}
			
			String sql = "select count(*) as cnt from vwBoard" + where;
			
			rs = stat.executeQuery(sql);
			
			if (rs.next()) {
				return rs.getInt("cnt");
			}
			
		} catch (Exception e) {
			System.out.println("BoardDAO.getTotalCount()");
			e.printStackTrace();
		}
		
		return 0;
		
	}

	//AddOk 서블릿이 게시판에서 가장 큰 thread+1000 값을 주세요~
	public int getMaxThread() {
		
		try {

			String sql = "select nvl(max(thread), 0) + 1000 as thread from tblBoard";
			rs = stat.executeQuery(sql);
			
			if (rs.next()) {
				return rs.getInt("thread");
			}

		} catch (Exception e) {
			System.out.println("BoardDAO.getMaxThread()");
			e.printStackTrace();
		}
		
		return 0;
	}

	//AddOk 서블릿이 계층형 처리를 위해 요청
	public void updateThread(HashMap<String, Integer> map) {
		
		try {

			String sql = "update tblBoard set thread = thread - 1 where thread > ? and thread < ?";
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setInt(1, map.get("previousThread"));
			pstat.setInt(2, map.get("parentThread"));
			
			pstat.executeUpdate();

		} catch (Exception e) {
			System.out.println("BoardDAO.updateThread()");
			e.printStackTrace();
		}
		
	}
	
}










