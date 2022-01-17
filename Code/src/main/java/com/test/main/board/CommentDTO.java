package com.test.main.board;

public class CommentDTO {
	private String seq;
	private String id;
	private String content;
	private String regdate;
	private String bseq;
	
	private String name; //작성자명

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public String getBseq() {
		return bseq;
	}

	public void setBseq(String bseq) {
		this.bseq = bseq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
