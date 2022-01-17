<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Code</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp" %>
<style>
	
	
</style>
</head>
<body>
	<!-- board/list.jsp -->
	<main class="main">
		<%@include file="/WEB-INF/views/inc/header.jsp" %>
		<section class="content">
		
			<c:if test="${map.searchmode == 'y'}">
			<div style="text-align:center;margin:10px;color:#777;">'${map.word}'(으)로 검색한 ${list.size()}개의 게시물이 있습니다.</div>
			</c:if>
			
			<table class="table table-bordered list">
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>이름</th>
					<th>날짜</th>
					<th>읽음</th>
				</tr>
				<c:forEach items="${list}" var="dto">
				
				<%-- 
				<c:choose>
					<c:when test="${dto.readcount >= 10}">
						<tr class="info">
					</c:when>
					<c:otherwise><tr></c:otherwise>
				</c:choose> 
				--%>
				<tr>
					<td>${dto.seq}</td>
					<td>
					
						<a href="/code/board/view.do?seq=${dto.seq}&column=${map.column}&word=${map.word}&page=${nowPage}">${dto.subject}</a>
						
						<c:if test="${dto.commentcount > 0}">
						<span class="badge">${dto.commentcount}</span>
						</c:if>
						
						<c:if test="${dto.isnew <= 1}">
						<span class="label label-danger">new</span>
						</c:if>
					</td>
					<td>${dto.name}</td> 
					<td>${dto.regdate}</td>
					<td>${dto.readcount}</td>
				</tr>
				</c:forEach>
				<c:if test="${list.size() == 0}">
				<tr>
					<td colspan="5">게시물이 없습니다.</td>
				</tr>
				</c:if>
			</table>
			
			<div class="pagebar">${pagebar}</div>
			
			<div class="search">
				<form method="GET" action="/code/board/list.do">
				<table style="width:500px;margin:20px auto;">
					<tr>
						<td>
							<select name="column" class="form-control">
								<option value="subject">제목</option>
								<option value="content">내용</option>
								<option value="name">이름</option>
							</select>
						</td>
						<td>
							<input type="text" name="word" class="form-control" required>
						</td>
						<td>
							<input type="submit" value="검색하기" class="btn btn-default">
						</td>
						<c:if test="${not empty id}">
						<td>
							<input type="button" value="내글보기" class="btn btn-default" onclick="location.href='/code/board/list.do?column=name&word=${name}';">
						</td>
						</c:if>
					</tr>
				</table>
				</form>
			</div>			
			
			<div class="btns">
			
				<input type="button" value="목록보기"
					class="btn btn-default"
					onclick="location.href='/code/board/list.do';">
			
				<c:if test="${not empty id}">
				<input type="button" value="글쓰기"
					class="btn btn-primary"
					onclick="location.href='/code/board/add.do';">
				</c:if>
				
			</div>
			
		</section>
		<%@include file="/WEB-INF/views/inc/footer.jsp" %>
	</main>
	
	<script>
	
		<c:if test="${map.searchmode == 'y'}">
		//검색 상태를 유지
		$('select[name=column]').val('${map.column}');
		$('input[name=word]').val('${map.word}');
		</c:if>
	
	</script>
</body>
</html>







