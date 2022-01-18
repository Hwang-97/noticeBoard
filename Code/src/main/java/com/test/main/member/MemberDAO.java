package com.test.main.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.test.jdbc.DBUtil;

public class MemberDAO {

	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;
	
	public MemberDAO() {
		
		try {

			conn = DBUtil.open();
			stat = conn.createStatement();

		} catch (Exception e) {
			System.out.println("MemberDAO.MemberDAO()");
			e.printStackTrace();
		}
		
	}

	//LoginOk 서블릿이 아이디와 암호를 줄테니 개인 정보를 돌려주세요. > 로그인 처리
	public MemberDTO login(MemberDTO dto) {
		
		try {

			//? > 오라클 매개변수 > %s 역할
			String sql = "select * from tblUser where id=? and pw=?";
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, dto.getId()); //첫번째 ?
			pstat.setString(2, dto.getPw()); //두번째 ?
			
			rs = pstat.executeQuery();
			
			if (rs.next()) {
				
				MemberDTO result = new MemberDTO();
				
				result.setId(rs.getString("id"));
				result.setName(rs.getString("name"));
				result.setLv(rs.getString("lv"));
				
				return result;
				
			}

		} catch (Exception e) {
			System.out.println("MemberDAO.login()");
			e.printStackTrace();
		}
		
		return null;
	}

	
	//Member 서블릿이 아이디를 줄테니 게시물 수를 주세요~
	public int getCount(String id) {
	
		try {

			String sql = "select count(*) as cnt from tblBoard where id = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, id);
			
			rs = pstat.executeQuery();
			
			if (rs.next()) {
				return rs.getInt("cnt");
			}

		} catch (Exception e) {
			System.out.println("MemberDAO.getCount()");
			e.printStackTrace();
		}
		
		return 0;
	}

	public int getCcount(String id) {
		try {

			String sql = "select count(*) as cnt from tblComment where id = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, id);
			
			rs = pstat.executeQuery();
			
			if (rs.next()) {
				return rs.getInt("cnt");
			}

		} catch (Exception e) {
			System.out.println("MemberDAO.getCount()");
			e.printStackTrace();
		}
		
		return 0;
	}

	public ArrayList<MemberDTO> list() {
		
		try {

			String sql = "select tblUser.*, (select count(*) from tblBoard where id = tblUser.id) as count, (select count(*) from tblComment where id = tblUser.id) as ccount from tblUser order by lv desc, name asc";
			
			ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();
			
			rs = stat.executeQuery(sql);
			
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setLv(rs.getString("lv"));
				dto.setCount(rs.getInt("count"));
				dto.setCcount(rs.getInt("ccount"));
				
				list.add(dto);
			}
			return list;

		} catch (Exception e) {
			System.out.println("MemberDAO.list()");
			e.printStackTrace();
		}
		
		return null;
	}
	
}





