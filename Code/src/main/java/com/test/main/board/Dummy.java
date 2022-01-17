package com.test.main.board;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.test.jdbc.DBUtil;

public class Dummy {

	public static void main(String[] args) {
		
		try {

			Connection conn = null;
			PreparedStatement pstat = null;
			conn = DBUtil.open();
			
			String sql = "insert into tblBoard (seq, id, subject, content, regdate, readcount, userip) values (seqBoard.nextVal, ?, ?, ?, default, default, ?)";
			pstat = conn.prepareStatement(sql);
			
			for (int i=0; i<150; i++) {
				pstat.setString(1, "hong");      
				pstat.setString(2, i + "번째 게시물입니다."); 
				pstat.setString(3, "내용입니다."); 
				pstat.setString(4, "127.0.0.1");
				
				pstat.executeUpdate();
				System.out.println(i);
			}

		} catch (Exception e) {
			System.out.println("Dummy.main()");
			e.printStackTrace();
		}
		
	}
	
}








