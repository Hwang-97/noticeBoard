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
			
			<table class="table table-bordered list">
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>이름</th>
					<th>날짜</th>
					<th>읽음</th>
				</tr>
				<c:forEach items="${list}" var="dto">
				<tr>
					<td>${dto.seq}</td>
					<td>
					
						<a href="/code/board/view.do?seq=${dto.seq}">${dto.subject}</a>
						
						<c:if test="${dto.isnew <= 1}">
						<span class="label label-danger">new</span>
						</c:if>
					</td>
					<td>${dto.name}</td> 
					<td>${dto.regdate}</td>
					<td>${dto.readcount}</td>
				</tr>
				</c:forEach>
			</table>
			<div class="btns">
			
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
	
	</script>
</body>
</html>







